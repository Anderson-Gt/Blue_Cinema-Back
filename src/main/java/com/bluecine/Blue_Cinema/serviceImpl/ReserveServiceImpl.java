package com.bluecine.Blue_Cinema.serviceImpl;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.bluecine.Blue_Cinema.dto.EditReserveDto;
import com.bluecine.Blue_Cinema.dto.EmailBody;
import com.bluecine.Blue_Cinema.dto.ReserveDto;
import com.bluecine.Blue_Cinema.entity.Chair;
import com.bluecine.Blue_Cinema.entity.Movie;
import com.bluecine.Blue_Cinema.entity.Reserve;
import com.bluecine.Blue_Cinema.entity.Schedule;
import com.bluecine.Blue_Cinema.entity.User;
import com.bluecine.Blue_Cinema.repository.ReserveRepository;
import com.bluecine.Blue_Cinema.service.ChairService;
import com.bluecine.Blue_Cinema.service.MovieService;
import com.bluecine.Blue_Cinema.service.ReserveService;
import com.bluecine.Blue_Cinema.service.ScheduleService;
import com.bluecine.Blue_Cinema.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReserveServiceImpl implements ReserveService {

    @Autowired
    private ReserveRepository reserveRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private MovieService movieService;

    @Autowired
    private ChairService chairService;

    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private EmailService emailService;
    

    @Override
    @Transactional(readOnly = true)
    public Iterable<Reserve> findAll() {        
        return reserveRepository.findAll();
    }

    
    @Override
    @Transactional(readOnly = true)
    public Optional<Reserve> findById(Long id) {
        return reserveRepository.findById(id);
    }

    @Override
    @Transactional
    public Reserve save(Reserve reserve) {
        return reserveRepository.save(reserve);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        reserveRepository.deleteById(id);        
    }

    @Override
    @Transactional
    public boolean existsById(Long Id){
        return reserveRepository.existsById(Id);
    }

    @Override
    @Transactional
    public String createReserve(ReserveDto reserveDto){
        User user = userService.findByEmail(reserveDto.getEmail()).get();
        Movie movie = movieService.findById(reserveDto.getIdMovie()).get();
        Schedule schedule = scheduleService.findById(reserveDto.getIdSchedule()).get();
        Set<Chair> chairs = new HashSet<>();
        EmailBody emailBody = new EmailBody("Tu reserva ha sido registrada exitosamente");
        emailBody.setEmail(user.getEmail());
        String movieTitle = movie.getTitle();        
        String linkImage = movie.getImage();
        String genre = movie.getGender();
        String hour = schedule.getTime();
        double totalPrice = movie.getTicketValue()*(reserveDto.getChairs().size());
        StringBuilder infoChairs = new StringBuilder();

        for(int chairReserve:reserveDto.getChairs()){
            chairs.add(chairService.findById(chairReserve).get());
            infoChairs.append("#" + chairService.findById(chairReserve).get().getIdChair()+" ");
        };
        Reserve reserve = new Reserve(reserveDto.getIdSchedule(), user, movie, chairs);
        save(reserve);

        int idReserve = (int)reserve.getIdReserve();
        String stringChairs = infoChairs.toString();



        emailBody.setContent(buildEmail(user.getNames(), linkImage, movieTitle, idReserve, genre, hour, totalPrice, stringChairs));
        emailService.sendEmail(emailBody);
        
        return "Reserva creada exitosamente";

    }
    
    @Override
    @Transactional
    public String updateReserve(Long reserveId, EditReserveDto editReserveDto){
        Reserve reserve = findById(reserveId).get();

        Set<Chair> chairs = new HashSet<>();
        for(int chairReserve:editReserveDto.getChairs()){
            chairs.add(chairService.findById(chairReserve).get());
        }

        reserve.setChairs(chairs);
        reserve.updateReserve(chairs);

        save(reserve);

        return "Reserva actualizada";
    }

    @Override
    @Transactional
    public List<Reserve> readReservesByUser(String email){
        
        User user = userService.findByEmail(email).get();
        List<Reserve> reserves = findByUsers(user);
        return reserves;
    }

    @Override
    @Transactional
    public List<Integer> getReservedChairs(Integer idSchedule, Long idMovie){
        Movie movie = movieService.findById(idMovie).get();
        List<Reserve> reserves = findByIdScheduleAndMovies(idSchedule, movie);
        Set<Chair> chairs = new HashSet<>();
        
        for(Reserve chair:reserves){
            chairs.addAll(chair.getChairs());
        }

        List<Integer> chairsReserved = new LinkedList<Integer>();

        for(Chair chairReserved:chairs){
            chairsReserved.add(chairReserved.getIdChair());
        }
        return chairsReserved;
    }

    @Override
    @Transactional
    public List<Reserve>findByIdScheduleAndMovies(Integer idSchedule, Movie movie){
        return reserveRepository.findByIdScheduleAndMovies(idSchedule, movie);
    }

    @Override
    @Transactional
    public List<Reserve>findByUsers(User user){
        return reserveRepository.findByUsers(user);
    }





    public String buildEmail(String name,String movieLink, String movieTitle, Integer idReserve, String genre, String hour, double price, String chairs) {
        return 
                "<head>\n"+
                "<style type=\"text/css\">\n"+
                "a {text-decoration: none;}\n"+
                "</style>\n"+
                "<![endif]-->\n"+
                "<!--[if gte mso 9]><style>sup { font-size: 100% !important; }</style><![endif]-->\n"+
                "<!--[if gte mso 9]>\n"+
                "<xml>\n"+
                "<o:OfficeDocumentSettings>\n"+
                "<o:AllowPNG></o:AllowPNG>\n"+
                "<o:PixelsPerInch>96</o:PixelsPerInch>\n"+
                "</o:OfficeDocumentSettings>\n"+
                "</xml>\n"+
                "<![endif]-->\n"+
                "<!--[if !mso]><!-- -->\n"+
                "<link href=\"https://fonts.googleapis.com/css?family=Merriweather:400,400i,700,700i\" rel=\"stylesheet\">\n"+
                "<!--<![endif]-->\n"+
                "<style type=\"text/css\">\n"+
                "#outlook a {\n"+
                "padding:0;\n"+
                "}\n"+
                ".ExternalClass {\n"+
                "width:100%;\n"+
                "}\n"+
                ".ExternalClass,\n"+
                ".ExternalClass p,\n"+
                ".ExternalClass span,\n"+
                ".ExternalClass font,\n"+
                ".ExternalClass td,\n"+
                ".ExternalClass div {\n"+
                "line-height:100%;\n"+
                "}\n"+
                ".es-button {\n"+
                "mso-style-priority:100!important;\n"+
                "text-decoration:none!important;\n"+
                "}\n"+
                "a[x-apple-data-detectors] {\n"+
                "color:inherit!important;\n"+
                "text-decoration:none!important;\n"+
                "font-size:inherit!important;\n"+
                "font-family:inherit!important;\n"+
                "font-weight:inherit!important;\n"+
                "line-height:inherit!important;\n"+
                "}\n"+
                ".es-desk-hidden {\n"+
                "display:none;\n"+
                "float:left;\n"+
                "overflow:hidden;\n"+
                "width:0;\n"+
                "max-height:0;\n"+
                "line-height:0;\n"+
                "mso-hide:all;\n"+
                "}\n"+
                "[data-ogsb] .es-button {\n"+
                "border-width:0!important;\n"+
                "padding:10px 25px 10px 25px!important;\n"+
                "}\n"+
                "@media only screen and (max-width:600px) {p, ul li, ol li, a { line-height:150%!important } h1, h2, h3, h1 a, h2 a, h3 a { line-height:120%!important } h1 { font-size:30px!important; text-align:center } h2 { font-size:26px!important; text-align:center } h3 { font-size:20px!important; text-align:center } h1 a { text-align:center } .es-header-body h1 a, .es-content-body h1 a, .es-footer-body h1 a { font-size:30px!important } h2 a { text-align:center } .es-header-body h2 a, .es-content-body h2 a, .es-footer-body h2 a { font-size:26px!important } h3 a { text-align:center } .es-header-body h3 a, .es-content-body h3 a, .es-footer-body h3 a { font-size:20px!important } .es-menu td a { font-size:14px!important } .es-header-body p, .es-header-body ul li, .es-header-body ol li, .es-header-body a { font-size:14px!important } .es-content-body p, .es-content-body ul li, .es-content-body ol li, .es-content-body a { font-size:14px!important } .es-footer-body p, .es-footer-body ul li, .es-footer-body ol li, .es-footer-body a { font-size:14px!important } .es-infoblock p, .es-infoblock ul li, .es-infoblock ol li, .es-infoblock a { font-size:12px!important } *[class=\"gmail-fix\"] { display:none!important } .es-m-txt-c, .es-m-txt-c h1, .es-m-txt-c h2, .es-m-txt-c h3 { text-align:center!important } .es-m-txt-r, .es-m-txt-r h1, .es-m-txt-r h2, .es-m-txt-r h3 { text-align:right!important } .es-m-txt-l, .es-m-txt-l h1, .es-m-txt-l h2, .es-m-txt-l h3 { text-align:left!important } .es-m-txt-r img, .es-m-txt-c img, .es-m-txt-l img { display:inline!important } .es-button-border { display:inline-block!important } a.es-button, button.es-button { font-size:18px!important; display:inline-block!important } .es-btn-fw { border-width:10px 0px!important; text-align:center!important } .es-adaptive table, .es-btn-fw, .es-btn-fw-brdr, .es-left, .es-right { width:100%!important } .es-content table, .es-header table, .es-footer table, .es-content, .es-footer, .es-header { width:100%!important; max-width:600px!important } .es-adapt-td { display:block!important; width:100%!important } .adapt-img { width:100%!important; height:auto!important } .es-m-p0 { padding:0px!important } .es-m-p0r { padding-right:0px!important } .es-m-p0l { padding-left:0px!important } .es-m-p0t { padding-top:0px!important } .es-m-p0b { padding-bottom:0!important } .es-m-p20b { padding-bottom:20px!important } .es-mobile-hidden, .es-hidden { display:none!important } tr.es-desk-hidden, td.es-desk-hidden, table.es-desk-hidden { width:auto!important; overflow:visible!important; float:none!important; max-height:inherit!important; line-height:inherit!important } tr.es-desk-hidden { display:table-row!important } table.es-desk-hidden { display:table!important } td.es-desk-menu-hidden { display:table-cell!important } .es-menu td { width:1%!important } table.es-table-not-adapt, .esd-block-html table { width:auto!important } table.es-social { display:inline-block!important } table.es-social td { display:inline-block!important } }\n"+
                "</style>\n"+
                "</head>\n"+
                "<body style=\"width:100%;font-family:arial, 'helvetica neue', helvetica, sans-serif;-webkit-text-size-adjust:100%;-ms-text-size-adjust:100%;padding:0;Margin:0\">\n"+
                "<div class=\"es-wrapper-color\" style=\"background-color:#F6F6F6\">\n"+
                "<!--[if gte mso 9]>\n"+
                "<v:background xmlns:v=\"urn:schemas-microsoft-com:vml\" fill=\"t\">\n"+
                "<v:fill type=\"tile\" color=\"#f6f6f6\"></v:fill>\n"+
                "</v:background>\n"+
                "<![endif]-->\n"+
                "<table class=\"es-wrapper\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;padding:0;Margin:0;width:100%;height:100%;background-repeat:repeat;background-position:center top\">\n"+
                "<tr style=\"border-collapse:collapse\">\n"+
                "<td valign=\"top\" style=\"padding:0;Margin:0\">\n"+
                "<table class=\"es-header\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;table-layout:fixed !important;width:100%;background-color:transparent;background-repeat:repeat;background-position:center top\">\n"+
                "<tr style=\"border-collapse:collapse\">\n"+
                "<td align=\"center\" style=\"padding:0;Margin:0\">\n"+
                "<table class=\"es-header-body\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;background-color:#ffffff;width:600px\" cellspacing=\"0\" cellpadding=\"0\" bgcolor=\"#ffffff\" align=\"center\">\n"+
                "<tr style=\"border-collapse:collapse\">\n"+
                "<td style=\"padding:0;Margin:0;padding-top:10px;padding-bottom:10px;background-color:#0f0f0f;border-radius:5px\" bgcolor=\"#0f0f0f\" align=\"left\">\n"+
                "<!--[if mso]><table style=\"width:600px\" cellpadding=\"0\" cellspacing=\"0\"><tr><td style=\"width:135px\" valign=\"top\"><![endif]-->\n"+
                "<table class=\"es-left\" cellspacing=\"0\" cellpadding=\"0\" align=\"left\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;float:left\">\n"+
                "<tr style=\"border-collapse:collapse\">\n"+
                "<td class=\"es-m-p20b\" align=\"left\" style=\"padding:0;Margin:0;width:135px\">\n"+
                "<table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" role=\"presentation\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\n"+
                "<tr style=\"border-collapse:collapse\">\n"+
                "<td style=\"padding:0;Margin:0;font-size:0px\" align=\"right\"><img class=\"adapt-img\" src=\"https://taztfv.stripocdn.email/content/guids/videoImgGuid/images/tmovie.png\" alt style=\"display:block;border:0;outline:none;text-decoration:none;-ms-interpolation-mode:bicubic\" width=\"56\"></td>\n"+
                "</tr>\n"+
                "</table></td>\n"+
                "</tr>\n"+
                "</table>\n"+
                "<!--[if mso]></td><td style=\"width:0px\"></td><td style=\"width:465px\" valign=\"top\"><![endif]-->\n"+
                "<table class=\"es-right\" cellspacing=\"0\" cellpadding=\"0\" align=\"right\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;float:right\">\n"+
                "<tr style=\"border-collapse:collapse\">\n"+
                "<td align=\"left\" style=\"padding:0;Margin:0;width:465px\">\n"+
                "<table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" role=\"presentation\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\n"+
                "<tr style=\"border-collapse:collapse\">\n"+
                "<td style=\"padding:10px;Margin:0;font-size:0px\" align=\"left\"><img class=\"adapt-img\" src=\"https://taztfv.stripocdn.email/content/guids/9e29ab95-bcd2-4113-a931-6ce38f2de22b/images/bluecine.png\" alt style=\"display:block;border:0;outline:none;text-decoration:none;-ms-interpolation-mode:bicubic\" height=\"47\"></td>\n"+
                "</tr>\n"+
                "</table></td>\n"+
                "</tr>\n"+
                "</table>\n"+
                "<!--[if mso]></td></tr></table><![endif]--></td>\n"+
                "</tr>\n"+
                "<tr style=\"border-collapse:collapse\">\n"+
                "<td style=\"padding:10px;Margin:0;background-color:#1059ff\" bgcolor=\"#1059ff\" align=\"left\">\n"+
                "<table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\n"+
                "<tr style=\"border-collapse:collapse\">\n"+
                "<td valign=\"top\" align=\"center\" style=\"padding:0;Margin:0;width:580px\">\n"+
                "<table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" role=\"presentation\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\n"+
                "<tr style=\"border-collapse:collapse\">\n"+
                "<td align=\"left\" style=\"padding:0;Margin:0\"><h2 style=\"Margin:0;line-height:19px;mso-line-height-rule:exactly;font-family:tahoma, verdana, segoe, sans-serif;font-size:16px;font-style:normal;font-weight:normal;color:#f6f2f2;text-align:center\"><strong>Las mejores experiencias, están en nuestro cinema !</strong><br></h2></td>\n"+
                "</tr>\n"+
                "</table></td>\n"+
                "</tr>\n"+
                "</table></td>\n"+
                "</tr>\n"+
                "</table></td>\n"+
                "</tr>\n"+
                "</table>\n"+
                "<table class=\"es-content\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;table-layout:fixed !important;width:100%\">\n"+
                "<tr style=\"border-collapse:collapse\">\n"+
                "<td align=\"center\" style=\"padding:0;Margin:0\">\n"+
                "<table class=\"es-content-body\" cellspacing=\"0\" cellpadding=\"0\" bgcolor=\"#ffffff\" align=\"center\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;background-color:#FFFFFF;width:600px\">\n"+
                "<tr style=\"border-collapse:collapse\">\n"+
                "<td style=\"Margin:0;padding-top:40px;padding-bottom:40px;padding-left:40px;padding-right:40px;background-color:#f9f9f9\" bgcolor=\"#f9f9f9\" align=\"left\">\n"+
                "<table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\n"+
                "<tr style=\"border-collapse:collapse\">\n"+
                "<td valign=\"top\" align=\"center\" style=\"padding:0;Margin:0;width:520px\">\n"+
                "<table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" role=\"presentation\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\n"+
                "<tr style=\"border-collapse:collapse\">\n"+
                "<td align=\"center\" style=\"padding:0;Margin:0\"><h1 style=\"Margin:0;line-height:36px;mso-line-height-rule:exactly;font-family:tahoma, verdana, segoe, sans-serif;font-size:30px;font-style:normal;font-weight:normal;color:#666666\"><strong>Tu Reserva se ha registrado exitosamente.</strong></h1></td>\n"+
                "</tr>\n"+
                "<tr style=\"border-collapse:collapse\">\n"+
                "<td class=\"es-m-txt-c\" align=\"center\" style=\"padding:0;Margin:0;padding-top:20px\"><p style=\"Margin:0;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-family:arial, 'helvetica neue', helvetica, sans-serif;line-height:21px;color:#333333;font-size:14px\">Hola <strong>"+name+"</strong>, nos alegra que disfrutes lo que tenemos para ti en BlueCinema, a continuación te mostramos los detalles de tu reserva.<br></p></td>\n"+
                "</tr>\n"+
                "</table></td>\n"+
                "</tr>\n"+
                "</table></td>\n"+
                "</tr>\n"+
                "<tr style=\"border-collapse:collapse\">\n"+
                "<td align=\"left\" style=\"padding:0;Margin:0;padding-left:20px;padding-right:20px;padding-top:40px\">\n"+
                "<!--[if mso]><table dir=\"rtl\" style=\"width:560px\" cellpadding=\"0\"\n"+
                "cellspacing=\"0\"><tr><td dir=\"ltr\" style=\"width:268px\" valign=\"top\"><![endif]-->\n"+
                "<table cellspacing=\"0\" cellpadding=\"0\" align=\"right\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\n"+
                "<tr style=\"border-collapse:collapse\">\n"+
                "<td align=\"left\" style=\"padding:0;Margin:0;width:268px\">\n"+
                "<table style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;border-left:1px solid #cccccc;border-right:1px solid #cccccc;border-top:1px solid #cccccc;border-bottom:1px solid #cccccc\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" role=\"presentation\">\n"+
                "<tr style=\"border-collapse:collapse\">\n"+
                "<td style=\"Margin:0;padding-top:10px;padding-bottom:10px;padding-left:15px;padding-right:15px;font-size:0px\" align=\"center\"><a target=\"_blank\" href=\"https://viewstripo.email/\" style=\"-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;text-decoration:none;color:#B0883F;font-size:14px\"><img src=\""+movieLink+"\" alt=\"Summit XL Padded Tub Chair\" style=\"display:block;border:0;outline:none;text-decoration:none;-ms-interpolation-mode:bicubic\" title=\"Summit XL Padded Tub Chair\" class=\"adapt-img\" width=\"236\"></a></td>\n"+
                "</tr>\n"+
                "</table></td>\n"+
                "</tr>\n"+
                "</table>\n"+
                "<!--[if mso]></td><td dir=\"ltr\" style=\"width:20px\"></td><td dir=\"ltr\" style=\"width:272px\" valign=\"top\"><![endif]-->\n"+
                "<table class=\"es-left\" cellspacing=\"0\" cellpadding=\"0\" align=\"left\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;float:left\">\n"+
                "<tr style=\"border-collapse:collapse\">\n"+
                "<td class=\"es-m-p0r\" valign=\"top\" align=\"center\" style=\"padding:0;Margin:0;width:272px\">\n"+
                "<table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" role=\"presentation\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\n"+
                "<tr style=\"border-collapse:collapse\">\n"+
                "<td align=\"center\" style=\"padding:20px;Margin:0\"><h2 class=\"product-name\" style=\"Margin:0;line-height:24px;mso-line-height-rule:exactly;font-family:tahoma, verdana, segoe, sans-serif;font-size:24px;font-style:normal;font-weight:normal;color:#333333;text-align:center\"><strong><a href="+movieLink+" style=\"-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;text-decoration:none;color:#666666;font-size:24px;font-family:merriweather, georgia, 'times new roman', serif\">"+movieTitle+"</a></strong><br></h2></td>\n"+
                "</tr>\n"+
                "</table></td>\n"+
                "</tr>\n"+
                "<tr style=\"border-collapse:collapse\">\n"+
                "<td class=\"es-m-p0r\" valign=\"top\" align=\"center\" style=\"padding:0;Margin:0;width:272px\">\n"+
                "<table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" role=\"presentation\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\n"+
                "<tr style=\"border-collapse:collapse\">\n"+
                "<td align=\"center\" style=\"padding:10px;Margin:0\"><h3 class=\"product-name\" style=\"Margin:0;line-height:20px;mso-line-height-rule:exactly;font-family:tahoma, verdana, segoe, sans-serif;font-size:20px;font-style:normal;font-weight:normal;color:#666666;text-align:left\"><strong>ID Reserva</strong>: #"+idReserve+"</h3></td>\n"+
                "</tr>\n"+
                "</table></td>\n"+
                "</tr>\n"+
                "<tr style=\"border-collapse:collapse\">\n"+
                "<td class=\"es-m-p0r\" valign=\"top\" align=\"center\" style=\"padding:0;Margin:0;width:272px\">\n"+
                "<table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" role=\"presentation\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\n"+
                "<tr style=\"border-collapse:collapse\">\n"+
                "<td align=\"left\" style=\"padding:10px;Margin:0\"><h3 class=\"product-name\" style=\"Margin:0;line-height:20px;mso-line-height-rule:exactly;font-family:tahoma, verdana, segoe, sans-serif;font-size:20px;font-style:normal;font-weight:normal;color:#666666;text-align:left\"><strong>Género</strong>: "+genre+"<br></h3></td>\n"+
                "</tr>\n"+
                "</table></td>\n"+
                "</tr>\n"+
                "<tr style=\"border-collapse:collapse\">\n"+
                "<td class=\"es-m-p0r\" valign=\"top\" align=\"center\" style=\"padding:0;Margin:0;width:272px\">\n"+
                "<table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" role=\"presentation\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\n"+
                "<tr style=\"border-collapse:collapse\">\n"+
                "<td align=\"center\" style=\"padding:10px;Margin:0\"><h3 class=\"product-name\" style=\"Margin:0;line-height:20px;mso-line-height-rule:exactly;font-family:tahoma, verdana, segoe, sans-serif;font-size:20px;font-style:normal;font-weight:normal;color:#666666;text-align:left\"><strong>Hora</strong>: "+hour+"</h3></td>\n"+
                "</tr>\n"+
                "</table></td>\n"+
                "</tr>\n"+
                "<tr style=\"border-collapse:collapse\">\n"+
                "<td class=\"es-m-p0r\" valign=\"top\" align=\"center\" style=\"padding:0;Margin:0;width:272px\">\n"+
                "<table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" role=\"presentation\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\n"+
                "<tr style=\"border-collapse:collapse\">\n"+
                "<td align=\"center\" style=\"padding:10px;Margin:0\"><h3 class=\"product-name\" style=\"Margin:0;line-height:20px;mso-line-height-rule:exactly;font-family:tahoma, verdana, segoe, sans-serif;font-size:20px;font-style:normal;font-weight:normal;color:#666666;text-align:left\"><strong>Valor reserva</strong>: $"+price+" COP</h3></td>\n"+
                "</tr>\n"+
                "</table></td>\n"+
                "</tr>\n"+
                "<tr style=\"border-collapse:collapse\">\n"+
                "<td class=\"es-m-p0r\" valign=\"top\" align=\"center\" style=\"padding:0;Margin:0;width:272px\">\n"+
                "<table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" role=\"presentation\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\n"+
                "<tr style=\"border-collapse:collapse\">\n"+
                "<td align=\"center\" style=\"padding:10px;Margin:0\"><h3 class=\"product-name\" style=\"Margin:0;line-height:20px;mso-line-height-rule:exactly;font-family:tahoma, verdana, segoe, sans-serif;font-size:20px;font-style:normal;font-weight:normal;color:#666666;text-align:left\"><strong>Sillas Reservadas</strong>: "+chairs+"<br></h3></td>\n"+
                "</tr>\n"+
                "</table></td>\n"+
                "</tr>\n"+
                "</table>\n"+
                "<!--[if mso]></td></tr></table><![endif]--></td>\n"+
                "</tr>\n"+
                "<tr style=\"border-collapse:collapse\">\n"+
                "<td align=\"left\" style=\"padding:0;Margin:0;padding-bottom:10px;padding-left:20px;padding-right:20px\">\n"+
                "<table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\n"+
                "<tr style=\"border-collapse:collapse\">\n"+
                "<td valign=\"top\" align=\"center\" style=\"padding:0;Margin:0;width:560px\">\n"+
                "<table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" role=\"presentation\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\n"+
                "<tr style=\"border-collapse:collapse\">\n"+
                "<td style=\"padding:0;Margin:0;padding-top:15px;padding-bottom:15px;font-size:0\" align=\"center\">\n"+
                "<table width=\"100%\" height=\"100%\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" role=\"presentation\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\n"+
                "<tr style=\"border-collapse:collapse\">\n"+
                "<td style=\"padding:0;Margin:0;border-bottom:1px solid #1059ff;background:#FFFFFF none repeat scroll 0% 0%;height:1px;width:100%;margin:0px\"></td>\n"+
                "</tr>\n"+
                "</table></td>\n"+
                "</tr>\n"+
                "</table></td>\n"+
                "</tr>\n"+
                "</table></td>\n"+
                "</tr>\n"+
                "</table></td>\n"+
                "</tr>\n"+
                "</table>\n"+
                "<table class=\"es-footer\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;table-layout:fixed !important;width:100%;background-color:transparent;background-repeat:repeat;background-position:center top\">\n"+
                "<tr style=\"border-collapse:collapse\">\n"+
                "<td align=\"center\" style=\"padding:0;Margin:0\">\n"+
                "<table class=\"es-footer-body\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;background-color:#1a1d2c;width:600px\" cellspacing=\"0\" cellpadding=\"0\" bgcolor=\"#1a1d2c\" align=\"center\">\n"+
                "<tr style=\"border-collapse:collapse\">\n"+
                "<td align=\"left\" style=\"padding:0;Margin:0\">\n"+
                "<table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\n"+
                "<tr style=\"border-collapse:collapse\">\n"+
                "<td valign=\"top\" align=\"center\" style=\"padding:0;Margin:0;width:600px\">\n"+
                "<table style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;background-color:#ffffff\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" bgcolor=\"#ffffff\" role=\"presentation\">\n"+
                "<tr style=\"border-collapse:collapse\">\n"+
                "<td style=\"padding:0;Margin:0;font-size:0px\" align=\"center\"><img class=\"adapt-img\" src=\"https://taztfv.stripocdn.email/content/guids/videoImgGuid/images/footerbg.jpeg\" alt style=\"display:block;border:0;outline:none;text-decoration:none;-ms-interpolation-mode:bicubic\" width=\"600\"></td>\n"+
                "</tr>\n"+
                "</table></td>\n"+
                "</tr>\n"+
                "</table></td>\n"+
                "</tr>\n"+
                "<tr style=\"border-collapse:collapse\">\n"+
                "<td style=\"Margin:0;padding-top:5px;padding-bottom:20px;padding-left:20px;padding-right:20px;background-color:#040000\" bgcolor=\"#040000\" align=\"left\">\n"+
                "<!--[if mso]><table style=\"width:560px\" cellpadding=\"0\" cellspacing=\"0\"><tr><td style=\"width:153px\"><![endif]-->\n"+
                "<table class=\"es-left\" cellspacing=\"0\" cellpadding=\"0\" align=\"left\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;float:left\">\n"+
                "<tr style=\"border-collapse:collapse\">\n"+
                "<td class=\"es-m-p0r es-m-p20b\" align=\"center\" style=\"padding:0;Margin:0;width:133px\">\n"+
                "<table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" role=\"presentation\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\n"+
                "<tr style=\"border-collapse:collapse\">\n"+
                "<td class=\"es-m-txt-c\" style=\"padding:0;Margin:0;font-size:0px\" align=\"center\"><a target=\"_blank\" href=\"https://viewstripo.email/\" style=\"-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;text-decoration:none;color:#FFFFFF;font-size:13px\"><img src=\"https://taztfv.stripocdn.email/content/guids/videoImgGuid/images/tmovie.png\" alt style=\"display:block;border:0;outline:none;text-decoration:none;-ms-interpolation-mode:bicubic\" width=\"63\"></a></td>\n"+
                "</tr>\n"+
                "</table></td>\n"+
                "<td class=\"es-hidden\" style=\"padding:0;Margin:0;width:20px\"></td>\n"+
                "</tr>\n"+
                "</table>\n"+
                "<!--[if mso]></td><td style=\"width:201px\"><![endif]-->\n"+
                "<table class=\"es-left\" cellspacing=\"0\" cellpadding=\"0\" align=\"left\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;float:left\">\n"+
                "<tr style=\"border-collapse:collapse\">\n"+
                "<td class=\"es-m-p0r es-m-p20b\" align=\"center\" style=\"padding:0;Margin:0;width:201px\">\n"+
                "<table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" role=\"presentation\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\n"+
                "<tr style=\"border-collapse:collapse\">\n"+
                "<td class=\"es-m-txt-с\" align=\"left\" style=\"padding:0;Margin:0;padding-bottom:10px\"><h3 style=\"Margin:0;line-height:24px;mso-line-height-rule:exactly;font-family:tahoma, verdana, segoe, sans-serif;font-size:20px;font-style:normal;font-weight:normal;color:#ffffff\">Contáctanos</h3></td>\n"+
                "</tr>\n"+
                "<tr style=\"border-collapse:collapse\">\n"+
                "<td class=\"es-m-txt-c\" align=\"left\" style=\"padding:0;Margin:0\"><p style=\"Margin:0;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-family:arial, 'helvetica neue', helvetica, sans-serif;line-height:20px;color:#ffffff;font-size:13px\">Celular: 3176602263</p><p style=\"Margin:0;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-family:arial, 'helvetica neue', helvetica, sans-serif;line-height:20px;color:#ffffff;font-size:13px\">Email:appbluecinema@gmail.com</p></td>\n"+
                "</tr>\n"+
                "</table></td>\n"+
                "</tr>\n"+
                "</table>\n"+
                "<!--[if mso]></td><td style=\"width:20px\"></td><td style=\"width:186px\"><![endif]-->\n"+
                "<table class=\"es-right\" cellspacing=\"0\" cellpadding=\"0\" align=\"right\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;float:right\">\n"+
                "<tr style=\"border-collapse:collapse\">\n"+
                "<td class=\"es-m-p0r es-m-p20b\" align=\"center\" style=\"padding:0;Margin:0;width:186px\">\n"+
                "<table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" role=\"presentation\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\n"+
                "<tr style=\"border-collapse:collapse\">\n"+
                "<td class=\"es-m-txt-с\" align=\"left\" style=\"padding:0;Margin:0;padding-bottom:10px\"><h3 style=\"Margin:0;line-height:24px;mso-line-height-rule:exactly;font-family:tahoma, verdana, segoe, sans-serif;font-size:20px;font-style:normal;font-weight:normal;color:#ffffff\">Ubicación<br></h3></td>\n"+
                "</tr>\n"+
                "<tr style=\"border-collapse:collapse\">\n"+
                "<td class=\"es-m-txt-c\" align=\"left\" style=\"padding:0;Margin:0\"><p style=\"Margin:0;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-family:arial, 'helvetica neue', helvetica, sans-serif;line-height:20px;color:#ffffff;font-size:13px\">Medellín - Antioquia (Colombia)<br></p></td>\n"+
                "</tr>\n"+
                "</table></td>\n"+
                "</tr>\n"+
                "</table>\n"+
                "<!--[if mso]></td></tr></table><![endif]--></td>\n"+
                "</tr>\n"+
                "</table></td>\n"+
                "</tr>\n"+
                "</table>\n"+
                "<table class=\"es-content\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;table-layout:fixed !important;width:100%\">\n"+
                "<tr style=\"border-collapse:collapse\">\n"+
                "<td align=\"center\" style=\"padding:0;Margin:0\">\n"+
                "<table class=\"es-content-body\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;background-color:transparent;width:600px\" cellspacing=\"0\" cellpadding=\"0\" bgcolor=\"#ffffff\" align=\"center\">\n"+
                "<tr style=\"border-collapse:collapse\">\n"+
                "<td align=\"left\" style=\"Margin:0;padding-bottom:10px;padding-top:20px;padding-left:20px;padding-right:20px\">\n"+
                "<table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\n"+
                "<tr style=\"border-collapse:collapse\">\n"+
                "<td valign=\"top\" align=\"center\" style=\"padding:0;Margin:0;width:560px\">\n"+
                "<table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" role=\"presentation\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\n"+
                "<tr style=\"border-collapse:collapse\">\n"+
                "<td class=\"es-infoblock\" align=\"center\" style=\"padding:0;Margin:0;line-height:14px;font-size:12px;color:#999999\"><p style=\"Margin:0;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-family:arial, 'helvetica neue', helvetica, sans-serif;line-height:14px;color:#999999;font-size:12px\">©2018 Adventure Inc. 120 Utah St., San Francisco, CA 98765<br></p></td>\n"+
                "</tr>\n"+
                "<tr style=\"border-collapse:collapse\">\n"+
                "<td class=\"es-infoblock\" align=\"center\" style=\"padding:0;Margin:0;padding-top:5px;padding-bottom:10px;line-height:14px;font-size:12px;color:#999999\"><p style=\"Margin:0;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-family:arial, 'helvetica neue', helvetica, sans-serif;line-height:14px;color:#999999;font-size:12px\">You are receiving this email because of your shopping activity at <a target=\"_blank\" href=\"http://www.adventure.com\" style=\"-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;text-decoration:none;color:#999999;font-size:12px\">www.adventure.com</a>. For&nbsp;information about our privacy practices, see our <strong><a target=\"_blank\" href=\"https://viewstripo.email\" style=\"-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;text-decoration:none;color:#999999;font-size:12px\">Privacy Policy</a></strong>.</p></td>\n"+
                "</tr>\n"+
                "<tr style=\"border-collapse:collapse\">\n"+
                "<td class=\"es-infoblock\" align=\"center\" style=\"padding:0;Margin:0;line-height:14px;font-size:12px;color:#999999\"><p style=\"Margin:0;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-family:arial, 'helvetica neue', helvetica, sans-serif;line-height:14px;color:#999999;font-size:12px\"><strong><a target=\"_blank\" href=\"https://viewstripo.email\" class=\"view\" style=\"-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;text-decoration:none;color:#999999;font-size:12px\">Web Version</a></strong>&nbsp;|&nbsp;<strong><a target=\"_blank\" href=\"https://viewstripo.email\" style=\"-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;text-decoration:none;color:#999999;font-size:12px\">Manage Preferences</a></strong>&nbsp;|&nbsp;<strong><a target=\"_blank\" class=\"view\" href=\"\" style=\"-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;text-decoration:none;color:#999999;font-size:12px\">Unsubscribe</a></strong></p></td>\n"+
                "</tr>\n"+
                "</table></td>\n"+
                "</tr>\n"+
                "</table></td>\n"+
                "</tr>\n"+
                "</table></td>\n"+
                "</tr>\n"+
                "</table></td>\n"+
                "</tr>\n"+
                "</table>\n"+
                "</div>\n"+
                "</body>\n"+
                "</html>\n";

    }


    
}

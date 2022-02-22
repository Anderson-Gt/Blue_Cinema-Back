package com.bluecine.Blue_Cinema.repository;

import com.bluecine.Blue_Cinema.dto.EmailBody;

public interface EmailPort {
    public boolean sendEmail(EmailBody emailBody);
}

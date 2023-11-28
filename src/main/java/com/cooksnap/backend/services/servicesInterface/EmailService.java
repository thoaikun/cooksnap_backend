package com.cooksnap.backend.services.servicesInterface;

import com.cooksnap.backend.domains.dto.requests.EmailDetails;

public interface EmailService {
    String sendTextMail(EmailDetails emailDetails);
    String sendAttachmentMail(EmailDetails emailDetails);
}

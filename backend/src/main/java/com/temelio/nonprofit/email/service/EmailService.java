package com.temelio.nonprofit.email.service;

import java.util.UUID;

public interface EmailService {

    String sendDonationNotifications(UUID foundationId);

}

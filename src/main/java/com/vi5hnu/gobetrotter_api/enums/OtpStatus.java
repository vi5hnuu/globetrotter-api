package com.vi5hnu.gobetrotter_api.enums;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum OtpStatus {
    USED("USED"),
    REVOKED("REVOKED"),
    UN_USED("UN_USED");
    public final String status;
}

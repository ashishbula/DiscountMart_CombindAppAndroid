package in.discountmart.utility_services.model.request_model;

public class VerifyOtpRequest extends DataRequest {
    String OTP;//":"42608","
    String OTPID;//":"30;

    public String getOTP() {
        return OTP;
    }

    public void setOTP(String OTP) {
        this.OTP = OTP;
    }

    public String getOTPID() {
        return OTPID;
    }

    public void setOTPID(String OTPID) {
        this.OTPID = OTPID;
    }
}

package springbootvinylecommercebackend.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class VietQRGenerateResponse {
    private String code;
    private String desc;
    private Data data;

    // Getters and Setters
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public static class Data {
        @JsonProperty("qrCode")
        private String qrCode;

        @JsonProperty("qrDataURL")
        private String qrDataURL;

        // Getters and Setters
        public String getQrCode() {
            return qrCode;
        }

        public void setQrCode(String qrCode) {
            this.qrCode = qrCode;
        }

        public String getQrDataURL() {
            return qrDataURL;
        }

        public void setQrDataURL(String qrDataURL) {
            this.qrDataURL = qrDataURL;
        }
    }
}

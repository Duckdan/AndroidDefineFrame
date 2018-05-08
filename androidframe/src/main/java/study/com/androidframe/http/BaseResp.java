package study.com.androidframe.http;

/**
 * 服务器数据解析模板示例
 */

public class BaseResp<T> {
    private Meta meta;
    private T data;

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public class Meta {
        private int code;
        private String message;

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        @Override
        public String toString() {
            return "Order{" +
                    "code='" + code + '\'' +
                    ", message='" + message + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "BaseModel{" +
                "meta=" + meta +
                ", data=" + data +
                '}';
    }
}

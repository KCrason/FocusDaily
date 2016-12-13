package site.krason.focusdaily.internet.http;

/**
 * @author Created by KCrason on 2016/12/12.
 * @email 535089696@qq.com
 */

public class BaseResult<T> {


    private int resultCode;

    public String getResultMessage() {
        return resultMessage;
    }

    public void setResultMessage(String resultMessage) {
        this.resultMessage = resultMessage;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    private String resultMessage;
    private T data;

}

package site.krason.focusdaily.internet;

/**
 * @author Created by KCrason on 2016/12/12.
 * @email 535089696@qq.com
 */

public class ApiExcepiton extends RuntimeException {

    public ApiExcepiton(int resultCode) {
        this(getErrorMessage(resultCode));
    }

    public ApiExcepiton(String errorMessage) {
        super(errorMessage);
    }

    public static String getErrorMessage(int resultCode) {
        switch (resultCode) {
            case 404:
                return "错误";
            default:
                return "未知错误";
        }
    }
}

package org.isabella.revdol.util;

import lombok.Data;

/***
 *  统一返回值格式
 */
@Data
public class CommonReturnType {
    private String status;
    private Object data;


    public static CommonReturnType create(Object data){
        return CommonReturnType.create(data,"success");
    }

    public static CommonReturnType create(Object data, String status) {
        CommonReturnType commonReturn = new CommonReturnType();
        commonReturn.setStatus(status);
        commonReturn.setData(data);
        return commonReturn;
    }

}
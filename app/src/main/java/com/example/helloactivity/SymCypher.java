package com.example.helloactivity;

public class SymCypher {
    private String strKey;
    private String type;
    private String input;
    private String output;
    private String mode;

    private AESWrapper aesWrapperObj;
    private SM4Wrapper sm4WrapperObj;


    public SymCypher(String strKey, String type, String mode, String input){
        this.strKey = strKey;
        this.type = type;
        this.input = input;
        this.mode = mode;
    }


    public String run(int times){
        if(type.equals("aes")){
            aesWrapperObj = new AESWrapper(strKey, input);
            output = aesWrapperObj.execute(mode, times);
        }
        else if(type.equals("sm4")){
            sm4WrapperObj = new SM4Wrapper(strKey, input);
            output = sm4WrapperObj.execute(mode, times);
        }
        System.out.println(output);
        return output;
    }
}

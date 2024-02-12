package com.rohit.utility;

import com.rohit.model.Employee;
import java.util.regex.*;
public class ValidatePayload {
    public boolean checkAll(Employee e)
    {
        boolean result;
        result = checkID(e.getEmpId())
                && checkEmail(e.getEmail())
                && checkName(e.getName())
                && checkPassword(e.getPassword())
                && checkNumber(e.getNumber());
        return result;
    }
    public boolean checkID(String empId)
    {
        return empId.length()==5;
    }
    public boolean checkPassword(String password)
    {
        return true;
    }
    public boolean checkNumber(String number)
    {
        if(number.length()==10)
        {
            String regex = "[0-9]+";
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(number);
            return m.matches();
        }
        return false;
    }
    public boolean checkName(String name)
    {
        return name.length()>4;
    }
    public boolean checkEmail(String email)
    {
        String regex = "^[A-Za-z0-9+_.-]+@genesys.com";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(email);
        return m.matches();
    }
}

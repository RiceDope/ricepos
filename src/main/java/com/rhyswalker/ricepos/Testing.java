package com.rhyswalker.ricepos;

import java.util.ArrayList;
import org.json.JSONObject;

public class Testing{

    public static void main(String[] args) {

        FileManagement fileManagement = new FileManagement();

        JSONObject receipt = fileManagement.getReceiptByID(1);

        fileManagement.addRefund(receipt);

    }
}
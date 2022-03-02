package com.example.task2;

import java.lang.*;
import java.util.Collections;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btnCount;
    private TextView textMaskLength;
    private TextView textAddressRange;
    private TextView textSubnetAddress;
    private TextView textBroadcastAddress;
    private EditText textIpAddress1;
    private EditText textIpAddress2;
    private EditText textIpAddress3;
    private EditText textIpAddress4;
    private EditText textMask1;
    private EditText textMask2;
    private EditText textMask3;
    private EditText textMask4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    private void initViews() {
        btnCount = (Button) findViewById(R.id.btnCount);
        textMaskLength = (TextView) findViewById(R.id.maskLength);
        textAddressRange = (TextView) findViewById(R.id.addressRange);
        textSubnetAddress = (TextView) findViewById(R.id.subnetAddress);
        textBroadcastAddress = (TextView) findViewById(R.id.broadcastAddress);
        textIpAddress1 = (EditText) findViewById(R.id.ipAddress1);
        textIpAddress2 = (EditText) findViewById(R.id.ipAddress2);
        textIpAddress3 = (EditText) findViewById(R.id.ipAddress3);
        textIpAddress4 = (EditText) findViewById(R.id.ipAddress4);
        textMask1 = (EditText) findViewById(R.id.mask1);
        textMask2 = (EditText) findViewById(R.id.mask2);
        textMask3 = (EditText) findViewById(R.id.mask3);
        textMask4 = (EditText) findViewById(R.id.mask4);

        btnCount.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnCount:
                Integer ip1 = Integer.parseInt(textIpAddress1.getText().toString());
                Integer ip2 = Integer.parseInt(textIpAddress2.getText().toString());
                Integer ip3 = Integer.parseInt(textIpAddress3.getText().toString());
                Integer ip4 = Integer.parseInt(textIpAddress4.getText().toString());
                Integer mask1 = Integer.parseInt(textMask1.getText().toString());
                Integer mask2 = Integer.parseInt(textMask2.getText().toString());
                Integer mask3 = Integer.parseInt(textMask3.getText().toString());
                Integer mask4 = Integer.parseInt(textMask4.getText().toString());

//                Integer mask1 = 255;
//                Integer mask2 = 255;
//                Integer mask3 = 248;
//                Integer mask4 = 0;
//                Integer ip1 = 192;
//                Integer ip2 = 168;
//                Integer ip3 = 11;
//                Integer ip4 = 10;

                if (ip1 < 0 || ip1 > 255) {
                    break;
                }
                if (ip2 < 0 || ip2 > 255) {
                    break;
                }
                if (ip3 < 0 || ip3 > 255) {
                    break;
                }
                if (ip4 < 0 || ip4 > 255) {
                    break;
                }
                if (mask1 < 0 || mask1 > 255) {
                    break;
                }
                if (mask2 < 0 || mask2 > 255) {
                    break;
                }
                if (mask3 < 0 || mask3 > 255) {
                    break;
                }
                if (mask4 < 0 || mask4 > 255) {
                    break;
                }

                Integer lengthMask = 0;
                Integer[] arrayLengthMask = {countMaskLength(mask1), countMaskLength(mask2), countMaskLength(mask3), countMaskLength(mask4)};
                for (int i = 0; i < 4; i++) {
                    if (arrayLengthMask[i] == -1) {
                        textMaskLength.setText("Invalid mask");
                        break;
                    }
                    lengthMask += arrayLengthMask[i];
                }
                textMaskLength.setText(lengthMask.toString());

                String[] binarySubnetAddressArray = {getEightBytes(ip1 & mask1), getEightBytes(ip2 & mask2), getEightBytes(ip3 & mask3), getEightBytes(ip4 & mask4)};
                textSubnetAddress.setText(String.join(".", binarySubnetAddressArray));

                textAddressRange.setText(countAddressRange(String.join("", binarySubnetAddressArray)).toString());

                textBroadcastAddress.setText(getBroadcastAddress(new Integer[]{ip1, ip2, ip3, ip4}, new Integer[]{mask1, mask2, mask3, mask4}));


                break;
        }
    }

    public static Integer countMaskLength(Integer mask) {
        if (mask == 0) {
            return 0;
        } else if (mask == 255) {
            return 8;
        }
        String binaryMask = Integer.toBinaryString(mask);
        if (binaryMask.length() != 8) {
            return -1;
        }
        boolean zeroFound = false;
        Integer k = 0;
        for (int i = 0; i < 8; i++) {
            if (binaryMask.charAt(i) == '1') {
                if (zeroFound) {
                    return -1;
                }
                k++;
            } else {
                zeroFound = true;
            }
        }
        return k;
    }

    public static String getEightBytes(Integer x) {
        String newStr = Integer.toBinaryString(x);
        if (newStr.length() == 8) {
            return newStr;
        }
        return String.join("", Collections.nCopies(8 - newStr.length(), "0")) + newStr;
    }

    public static Integer countAddressRange(String address) {
        int i;
        for (i = address.length() - 1; i >= 0; i--) {
            if (address.charAt(i) == '1') {
                break;
            }
        }
        Integer zeros = address.length() - i - 1;
        return (int) Math.pow(2, zeros) - 2;
    }

    public static String getBroadcastAddress(Integer[] ip, Integer[] mask) {
        String[] broadcastAddress = new String[4];
        for (int i = 0; i < 4; i++) {
            broadcastAddress[i] = Integer.toString(ip[i] | (~(mask[i]) + 256));
        }
        return String.join(".", broadcastAddress);
    }
}
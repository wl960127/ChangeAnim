package com.wl.anim;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.PowerManager;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.util.Log;
import android.os.Bundle;
import android.view.View;


import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;


public class AnimActivity extends Activity implements AdapterView.OnItemClickListener {
    private final static String TAG = "com.wl.anim";

    private ListView mListView;
    private List<String> mAnimFileName = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        Log.i(TAG, "AnimActivity  onCreat");


        mListView =(ListView) findViewById(R.id.list_anim);

        //获取所有的开机动画文件名
        loadAnimFile();

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, mAnimFileName);

        mListView.setAdapter(arrayAdapter);
        mListView.setOnItemClickListener(this);

    }

    public void onClick_Reboot(View view) {
        new AlertDialog.Builder(this).setTitle("修改开机动画")
                .setMessage("是否重启")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        reboot1();   // 调用PowerManager.reboot 方法重启设备   也可以调用reboot方法，使用adb指令
                    }
                }).setNegativeButton("取消", null).show();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //修改开机动画
        changeBootAnim(mAnimFileName.get(position));
        android.widget.Toast.makeText(this, "已成功更换开机动画", Toast.LENGTH_SHORT).show();
    }

    public void loadAnimFile() {
        try {
            Process process = Runtime.getRuntime().exec("su");
            OutputStream os = process.getOutputStream();
            os.write("ls /sdcard/bootanim".getBytes());
            InputStreamReader isr = new InputStreamReader(process.getInputStream());
            BufferedReader br = new BufferedReader(isr);
            os.flush();
            os.close();

            String path = "";
            while ((path = br.readLine()) != null) {
                mAnimFileName.add(path);
            }
            if (mAnimFileName==null){
                android.widget.Toast.makeText(this, "没有获取到开机动画文件", Toast.LENGTH_SHORT).show();
            }

            isr.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void changeBootAnim(String animFileName) {
        try {
            Process process = Runtime.getRuntime().exec("su");
            DataOutputStream os = new DataOutputStream(process.getOutputStream());
            os.writeBytes("mount -o remount,rw /data \n");  //挂载 /data分区  以便该分区变为可写状态
            os.writeBytes("cp /sdcard/bootanim/" + animFileName + "/data/local/bootanimation.zip");
            os.flush();
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void reboot() {
        try {
            Process process = Runtime.getRuntime().exec("su");
            DataOutputStream os = new DataOutputStream(process.getOutputStream());
            os.writeBytes("reboot \n");
            os.flush();
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void reboot1() {
        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        pm.reboot("change boot animation");
    }

}
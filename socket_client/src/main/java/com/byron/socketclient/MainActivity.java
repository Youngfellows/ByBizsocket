package com.byron.socketclient;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;

import bizsocket.core.Configuration;
import bizsocket.utils.IPUtil;
import bizsocket.utils.StringValidation;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView mTvIp;
    private EditText mEdServerIp;
    private EditText mEdServerPort;
    private Button mBtnSendTV;
    private TextView mTvReceiveServer;
    private Button mBtnConnectTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initData();
        initListener();
    }

    private void initView() {
        mTvIp = findView(R.id.tv_ip); //显示本机IP
        mEdServerIp = findView(R.id.edt_tv_pi); //服务端IP
        mEdServerPort = findView(R.id.edt_tv_port);//服务端端口
        mBtnSendTV = findView(R.id.btn_send_voice); //发送到TV端
        mTvReceiveServer = findView(R.id.tv_receive_server); //接收服务端的消息
        mBtnConnectTv = findView(R.id.btn_connect_tv); //连接TV端

    }

    private void initData() {
//        getTestClient().connect();//服务端
        initLocalhostIp();
    }

    private void initLocalhostIp() {
        String ipAddress = IPUtil.getLocalIPAddress(true);
        mTvIp.setText("客户端IP: " + ipAddress);
    }

    private void initListener() {
        mBtnConnectTv.setOnClickListener(this);
        mBtnSendTV.setOnClickListener(this);
    }


    protected <T extends View> T findView(int viewId) {
        return (T) findViewById(viewId);
    }

    protected void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_connect_tv:
                // TODO: 2017/10/29 连接TV端
                showToast(" 连接TV端");
                startServer();
                break;

            case R.id.btn_send_voice:
                // TODO: 2017/10/29 发送数据到TV端
                showToast("发送数据到TV端");
                break;

            default:
                break;
        }
    }

    private void startServer() {
        // TODO: 2017/11/5 开启服务
        String ip = mEdServerIp.getText().toString();
        String port = mEdServerPort.getText().toString();

        if (!TextUtils.isEmpty(ip) && !TextUtils.isEmpty(port)) {
            if (StringValidation.validateRegex(ip, StringValidation.RegexIP) && StringValidation.validateRegex(port, StringValidation.RegexPort)) {
                ByronSampleClient client = new ByronSampleClient(new Configuration.Builder()
                        .host(ip)
                        .port(Integer.parseInt(port))
                        .readTimeout(TimeUnit.SECONDS, 30)
                        .heartbeat(60)
                        .build());
                client.startServer(client);
            } else {
                showToast("IP地址或端口号不合法");
            }
        } else {
            showToast("IP或端口号不能为空");
            return;
        }

    }
}

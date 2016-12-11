package com.carl_yang.email;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.carl_yang.mail.Mail;
import com.carl_yang.tools.common;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailActivity extends Activity {

    private Button email_send;
    private EditText email_receive_people_value;
    private ImageView qianming;
    private TextView email_body_value;
    private TextView email_main_value;

    String body_value="";
    Bitmap bitmapnew=null;

    private String imagePaht="/mnt/sdcard/test.png";
    private String pdfAddress="/mnt/sdcard/test.pdf";

    private void init(){
        email_send= (Button) findViewById(R.id.email_send);
        email_receive_people_value= (EditText) findViewById(R.id.email_receive_people_value);
        qianming= (ImageView) findViewById(R.id.qianming);
        email_body_value= (TextView) findViewById(R.id.email_body_value);
        email_main_value= (TextView) findViewById(R.id.email_main_value);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email);

//        BitmapDrawable draw=(BitmapDrawable) getResources().getDrawable(R.drawable.writepad);
//        mSignBitmap=draw.getBitmap();

        init();

//        body_value= BlueToothApplication.email_body;
        body_value="aaaaaaaaaaaaaaaaaaaaaaa";
        System.out.println("email 页面"+body_value);
        File file=new File(imagePaht);
        if (file.exists()) {
            Bitmap mSignBitmap= BitmapFactory.decodeFile(imagePaht, null);
            if(mSignBitmap!=null){
                // 下面这两句是对图片按照一定的比例缩放，这样就可以完美地显示出来。
                int scale = common.reckonThumbnail(mSignBitmap.getWidth(), mSignBitmap.getHeight(), 500, 600);
                bitmapnew = common.PicZoom(mSignBitmap, mSignBitmap.getWidth() / scale, mSignBitmap.getHeight() / scale);
                //由于Bitmap内存占用较大，这里需要回收内存，否则会报out of memory异常
                mSignBitmap.recycle();
            }
        }
//            mSignBitmap=BlueToothApplication.email_qianm;
        email_body_value.setText(body_value);
        qianming.setImageBitmap(bitmapnew);

        email_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String receAddress = email_receive_people_value.getText().toString();
                if (!"".equals(receAddress)) {
                    Pattern pattern = Pattern.compile("^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$");
                    Matcher matcher = pattern.matcher(receAddress);
                    System.out.println(matcher.matches());
                    if (matcher.matches()) {//判断邮箱格式是否正确
                        Log.i("aa", "++++++++++++++++++++++++++++");
                        final Mail email = new Mail("yang_carl@sina.com", "lin5832702");//发件人的用户名和密码
                        new AsyncTask<Void, Void, Integer>() {
                            @Override
                            protected Integer doInBackground(Void... params) {
                                int result = email.sendEmail(receAddress, email_main_value.getText().toString(), body_value, bitmapnew,pdfAddress);
                                return result;
                            }

                            protected void onPostExecute(Integer result) {
                                super.onPostExecute(result);
                                if (result == 1) {
                                    System.out.println("发送邮件成功!");
                                    Toast.makeText(EmailActivity.this, "发送邮件成功！", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }.execute();
                    } else {
                        Toast.makeText(EmailActivity.this, "邮箱地址格式错误！", Toast.LENGTH_SHORT).show();
                    }
                    ;
                } else {
                    Toast.makeText(EmailActivity.this, "收件人不能为空！", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}

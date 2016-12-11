package com.carl_yang.email;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


public class CatalActivity extends Activity {

    private String imagePath="/mnt/sdcard/test.png";
    private String pdfAddress="/mnt/sdcard/test.pdf";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catal);

    }

    public void onClick(View v){
        switch (v.getId()){
            case R.id.cata_pdf:
                StringToPdf();
                break;
            case R.id.cata_camera:
                useCamera();
                break;
            case R.id.cata_email:
                Intent intent =new Intent(this,EmailActivity.class);
                startActivity(intent);
                break;
        }
    }

    /**
     * 调用相机拍照
     */
    private void useCamera(){
        System.out.println("----=======调用拍照=====-------:");
        File temp=new File(imagePath);
        Uri imageUri=Uri.fromFile(temp);
        Intent it=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        it.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
        startActivityForResult(it,1);
    }

    /**
     * 相机拍照完后，接收的返回s
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if(resultCode==Activity.RESULT_OK){
                System.out.println("运单拍照完成.");
            }
        }
    }

    private void StringToPdf(){
        Document doc=new Document();
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(new File(pdfAddress));
            PdfWriter.getInstance(doc, fos);
            doc.open ();
            doc.setPageCount(1);
            String aa="";
            aa+="时间     最高值   最低值    状态\n";
            aa+="2016-11-11 10:00   22   11   报警\n";
            aa+="2016-11-11 10:00   22   11   报警\n";
            aa+="2016-11-11 10:00   22   11   报警\n";
            aa+="2016-11-11 10:00   22   11   报警\n";
            aa+="2016-11-11 10:00   22   11   报警\n";
            aa+="2016-11-11 10:00   22   11   报警\n";
            aa+="2016-11-11 10:00   22   11   报警\n";
            aa+="2016-11-11 10:00   22   11   报警\n";
            aa+="2016-11-11 10:00   22   11   报警\n";
            aa+="2016-11-11 10:00   22   11   报警\n";aa+="2016-11-11 10:00   22   11   报警\n";
            aa+="2016-11-11 10:00   22   11   报警\n";
            aa+="2016-11-11 10:00   22   11   报警\n";
            aa+="2016-11-11 10:00   22   11   报警\n";
            aa+="2016-11-11 10:00   22   11   报警\n";aa+="2016-11-11 10:00   22   11   报警\n";
            aa+="2016-11-11 10:00   22   11   报警\n";
            aa+="2016-11-11 10:00   22   11   报警\n";
            aa+="2016-11-11 10:00   22   11   报警\n";
            aa+="2016-11-11 10:00   22   11   报警\n";aa+="2016-11-11 10:00   22   11   报警\n";
            aa+="2016-11-11 10:00   22   11   报警\n";
            aa+="2016-11-11 10:00   22   11   报警\n";
            aa+="2016-11-11 10:00   22   11   报警\n";
            aa+="2016-11-11 10:00   22   11   报警\n";aa+="2016-11-11 10:00   22   11   报警\n";
            aa+="2016-11-11 10:00   22   11   报警\n";
            doc.add(new Paragraph(aa, setChineseFont()));
            //一定要记得关闭document对象
            doc.close();
            fos.flush();
            fos.close();
        } catch (Exception e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        Toast.makeText(getApplicationContext(),"PDA生成成功!",Toast.LENGTH_LONG).show();
    }

    // 产生PDF字体
    public static Font setChineseFont() {
        BaseFont bf = null;
        Font fontChinese = null;
        try {
            bf = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H",BaseFont.NOT_EMBEDDED);
            fontChinese = new Font(bf, 12, Font.NORMAL);
        } catch (DocumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return fontChinese;
    }
}

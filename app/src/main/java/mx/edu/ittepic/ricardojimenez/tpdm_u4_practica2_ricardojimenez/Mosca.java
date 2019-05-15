package mx.edu.ittepic.ricardojimenez.tpdm_u4_practica2_ricardojimenez;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

import java.util.Random;

public class Mosca  {
    Bitmap imagen;
    int x,y,vida,maxH,maxW;
    Random rdn;

    public Mosca(int mh,int mw,int vida,int img,Lienzo l) {
        maxH=mh;
        maxW=mw;
        rdn = new Random();
        this.vida = vida;
        imagen = BitmapFactory.decodeResource(l.getResources(),img);
        this.x = rdn.nextInt(maxW-imagen.getWidth()+1)+20;
        this.y = rdn.nextInt(maxH-imagen.getHeight()+1)+80;
    }
    public void pintar(Canvas c, Paint p){
        c.drawBitmap(imagen,x,y,p);
    }
    public boolean estaEnArea(int dedox,int dedoy) {
        int x2=x+imagen.getWidth();
        int y2=y+imagen.getHeight();
        if(dedox >=x && dedox<=x2 && dedoy >=y && dedoy<=y2){
            return true;
        }
        return false;
    }
}

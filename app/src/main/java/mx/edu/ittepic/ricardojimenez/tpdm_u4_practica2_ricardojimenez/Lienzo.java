package mx.edu.ittepic.ricardojimenez.tpdm_u4_practica2_ricardojimenez;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.View;

import java.util.ArrayList;
import java.util.List;



public class Lienzo extends View {
//    SurfaceHolder holder;
    Thread decometro,dibujo;
    List<Mosca> moscas;
    int tiempo,maxH,maxW,score;
    boolean boss,fin;
    public Lienzo(Context context) {
        super(context);
        tiempo=60;
        moscas = new ArrayList<>();
        maxW = getResources().getSystem().getDisplayMetrics().widthPixels;
        maxH = getResources().getSystem().getDisplayMetrics().heightPixels-200;
        boss =false;
        fin=false;
        decometro = new Thread(){
            @Override
            public void run() {
                while (true){
                    if(tiempo==0 || fin){
                        moscas.clear();
                        invalidate();
                        break;
                    }
                    tiempo--;
                    if(score==30 && !boss){
                        moscas.clear();
                        moscas.add(new Mosca(maxH,maxW,5,R.drawable.jefe,Lienzo.this));
                        tiempo=10;
                        boss=true;
                    }
                    if(!boss){
                        moscas.add(new Mosca(maxH,maxW,1,R.drawable.mosca, Lienzo.this));
                    }
                    invalidate();
                    try {
                        sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        decometro.start();

    }

    protected void onDraw(Canvas c) {
        Paint p = new Paint();
        p.setTextSize(70);
        if(boss){
            p.setColor(Color.RED);
        }
        p.setFakeBoldText(true);
        c.drawText(""+tiempo,maxW/2-20,60,p);
        if(!fin)
        for (int i=0; i<moscas.size();i++){
            moscas.get(i).pintar(c,p);
        }
        else
            c.drawText("FELICIDADES!!",20,maxH/2,p);
        if(tiempo==0 && !fin){
            c.drawText("PERDISTE!! ",20,maxH/2,p);
        }
    }
    public boolean onTouchEvent(MotionEvent me){
//        el evento onTochEvent permite detectar los toques
//        de uno o mas dedos que se hacen en el area de dibujo

        int accion = me.getAction();
        int posx= (int)me.getX();
        int posy= (int)me.getY();

        switch (accion){
            case MotionEvent.ACTION_DOWN: //presinado
                for (int i=0; i<moscas.size();i++) {
                    int vida = moscas.get(i).vida;
                    if(moscas.get(i).estaEnArea(posx,posy)) {
                        if(vida==1){
                            moscas.remove(i);
                            score++;
                        }
                        if(boss){

                            vida--;
                            if(vida==0 && boss){
                                fin=true;
                                break;
                            }
                            moscas.remove(i);
                            moscas.add(new Mosca(maxH,maxW,vida,R.drawable.jefe,Lienzo.this));

                        }
                    }
                }
                break;
            case MotionEvent.ACTION_MOVE: //moviendose sin soltar


                break;
            case MotionEvent.ACTION_UP:  //soltar

                break;
        }
        invalidate();
        return true;
    }
}

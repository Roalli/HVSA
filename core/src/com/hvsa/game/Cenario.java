package com.hvsa.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.TimeUtils;
import java.util.Iterator;

public class Cenario {

    private float vel;
    private int numNuvens, numNuvensPadrao;
    private Texture img;
    private Array<Sprite> nuvens;
    private long lastNuvemTime;
    private TextureRegion tipo;

    private float distancia;

    public Cenario(){

        img = new Texture("nuvem.png");

        nuvens = new Array<Sprite>();
        lastNuvemTime = 0;
        vel = 1;
        numNuvens = 399999999;
        numNuvensPadrao = 399999999;
        
        
        

    }

    public void Draw(Batch batch, float velocidade){

        vel = velocidade;
        numNuvens = (int)(numNuvensPadrao / vel);

        moveNuvens();

        for(Sprite nuvem : nuvens){
            batch.draw(nuvem, nuvem.getX(), nuvem.getY());
        }

    }

    private void spawnNuvem(){//Metodo para criar nuvens novas 

        Sprite nuvem = new Sprite(img);
        nuvem.setX(Gdx.graphics.getWidth());
        nuvem.setY(MathUtils.random(-(img.getHeight()/2), Gdx.graphics.getHeight() - (img.getHeight()/2)));

        nuvens.add(nuvem);
        lastNuvemTime = TimeUtils.nanoTime();


    }

    private void moveNuvens(){

        if( TimeUtils.nanoTime() - lastNuvemTime > numNuvens ){
            this.spawnNuvem();
        }

        for( Iterator<Sprite> iter = nuvens.iterator(); iter.hasNext(); ){

            Sprite nuvem = iter.next();
            nuvem.setX((int)((nuvem.getX()) - (vel * (800 * Gdx.graphics.getDeltaTime()))));
        
            if(nuvem.getX() + img.getWidth() < 0){
                iter.remove();
            }
        }
        
        

    }

    public void reset(){

        lastNuvemTime = 0;
        numNuvens = numNuvensPadrao;
        nuvens.clear();

    }

    public void dispose(){
        img.dispose();
    }

}

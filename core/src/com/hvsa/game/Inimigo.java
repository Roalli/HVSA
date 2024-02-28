package com.hvsa.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.TimeUtils;

import java.text.BreakIterator;
import java.util.Iterator;

public class Inimigo{

    private float vel;
    private Array<Sprite> Inimigos;
    private Texture img;
    private int numNave, numNavePadrao;
    private long lastNaveTime;

    public Inimigo(){

        img = new Texture("nave1.png");
        Inimigos = new Array<Sprite>();
        numNave = 799999999;
        numNavePadrao = 799999999;
        vel = 1;
        lastNaveTime = 0;



    }


    public void Draw(Batch batch, float velocidade) {

        vel = velocidade;

        numNave = (int)(numNavePadrao / vel);

        this.moveInimigo();

        for( Sprite inimigo : Inimigos){

            batch.draw(inimigo, inimigo.getX(), inimigo.getY());

        }


    }

    private void spawnInimigo(){

        Sprite nave = new Sprite(img);

        nave.setX(Gdx.graphics.getWidth());
        nave.setY(MathUtils.random(-(img.getHeight()), Gdx.graphics.getHeight() - (img.getHeight())));

        Inimigos.add(nave);
        lastNaveTime = TimeUtils.nanoTime();
        

    }

    private void moveInimigo(){


        if( TimeUtils.nanoTime() - lastNaveTime > numNave ){
            this.spawnInimigo();
        }

        for( Iterator<Sprite> iter = Inimigos.iterator(); iter.hasNext(); ){

            Sprite nave = iter.next();
            nave.setX((int)((nave.getX()) - (vel * (400 * Gdx.graphics.getDeltaTime()))));
        
            if(nave.getX() + img.getWidth() < 0){
                iter.remove();
            }
        }

    }

    public boolean Colissao(float objx, float objy, float objw, float objh, boolean remove){

        boolean colisao = false;

        

        for( Iterator<Sprite> iter = Inimigos.iterator(); iter.hasNext(); ){

            Sprite inimigo = iter.next();

            if(inimigo.getX() + inimigo.getWidth() > objx && inimigo.getX() < objx + objw && inimigo.getY() + inimigo.getHeight() > objy + 10 && inimigo.getY() < objy + objh){

                if(remove){
                
                    iter.remove();
                }

                colisao = true;
                
            }

        }

        return colisao;

    }

    public void reset(){

        Inimigos.clear();
        lastNaveTime = 0;
        numNave = numNavePadrao;


    }
    public void dispose(){

        img.dispose();

    }

}

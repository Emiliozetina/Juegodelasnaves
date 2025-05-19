package objetos;

//Eauipo numero 4 
//Emilio Zetina, Valeri Skirlathze, Alfredo Vieto, Ricardo Restrepo 
//Proyecto final de semestre para la materia de POO


import entradas.teclado;
import estados.GameState;
import graphics.Animacion;
import graphics.Assets;
import graphics.Sonido;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import math.vector2D;

public class jugador extends MovObjeto {
    
    private vector2D heading; 
    private vector2D acceleration; 

    private boolean accelerating = false; 
    private long fireRate; 

    private boolean spawning, visible; 

    private long spawnTime, flickerTime, shieldTime, doubleScoreTime, fastFireTime, doubleGunTime;

    private Sonido shoot, loose; 

    private boolean shieldOn, doubleScoreOn, fastFireOn, doubleGunOn;

    private Animacion shieldEffect;

    private long fireSpeed;

    public jugador(vector2D position, vector2D velocity, double maxVel, BufferedImage texture, GameState gameState){
        super(position, velocity, maxVel, texture, gameState);
        heading = new vector2D(0, 1);
        acceleration = new vector2D();
        fireRate = 0; 
        spawnTime = 0; 
        flickerTime = 0; 
        shieldTime = 0; 
        fastFireTime = 0; 
        doubleGunTime = 0; 

        shoot = new Sonido(Assets.disparar);
        loose = new Sonido (Assets.perder);

        shieldEffect = new Animacion(Assets.efectoescudo, 80, null);

        visible = true;
    }

    public void setShield(){
        if(shieldOn){
            shieldTime = 0; 
        }
        shieldOn = true;
    }

    public boolean isShieldOn(){
        return shieldOn;
    }

    public void setDoubleScore(){
        if(doubleScoreOn){
            doubleScoreTime = 0; 
        }
        doubleScoreOn = true;
    }

    public boolean isDoubleScoreOn(){
        return doubleScoreOn;
    }

    public void setFastFire(){
        if(fastFireOn){
            fastFireTime = 0;
        }
        fastFireOn = true;
    }

    public void setDoubleGun(){
        if(doubleGunOn){
            doubleGunTime = 0;
        }
        doubleGunOn = true;
    }

    public boolean isSpawning(){
        return spawning;
    }

    @Override
    public void update(float dt){

        fireRate += dt;

        if(shieldOn){
            shieldTime += dt;
        }
        if(doubleScoreOn){
            doubleScoreTime +=dt;
        }
        if(fastFireOn){
            fireSpeed = Constantes.firerate /2;
            fastFireTime += dt;
        }
        else {
            fireSpeed = Constantes.firerate;
        }

        if(doubleGunOn){
            doubleGunTime += dt;
        }
        if(shieldTime > Constantes.ShieldTime){
            shieldTime = 0;
            shieldOn = false; 
        }
        if(doubleScoreTime > Constantes.DoubleScoreTime){
            doubleScoreOn = false; 
            doubleScoreTime = 0;
        }
        if(fastFireTime > Constantes.FastFireTime){
            fastFireOn = false; 
            fastFireTime = 0;
        }
        if(doubleGunTime > Constantes.DoubleGunTime){
            doubleGunOn = false; 
            doubleGunTime = 0;
        }

        if(spawning) {

            flickerTime += dt;
            spawnTime += dt;

            if(flickerTime > Constantes.flickertime){

                visible = !visible; 
                flickerTime = 0; 
            }

            if(spawnTime > Constantes.spawningtime){
                spawning = false; 
                visible = true;
            }
        }

        if(teclado.shoot && fireRate > fireSpeed && !spawning){

            if(doubleGunOn){
                vector2D leftGun = getCenter();
                vector2D rightGun = getCenter();

                vector2D temp = new vector2D(heading);
                temp.normal();
                temp = temp.setDireccion(angulo - 1.3f);
                temp = temp.escalar(width);
                rightGun = rightGun.sum(temp);

                temp = temp.setDireccion(angulo - 1.9f);
                leftGun = leftGun.sum(temp);

                Laser l = new Laser(leftGun, heading, Constantes.LaserVel, angulo, Assets.laserAzul, gameState);
                Laser r = new Laser(rightGun, heading, Constantes.LaserVel, angulo, Assets.laserAzul, gameState);     
                
                gameState.getMovObjeto().add(0, l);
                gameState.getMovObjeto().add(0, r);

            }

            else {
                gameState.getMovObjeto().add(0, new Laser(
                    getCenter().sum(heading.escalar(width)), 
                    heading, 
                    Constantes.LaserVel,
                    angulo,
                    Assets.laserAzul,
                     gameState));
            }

            fireRate = 0; 
            shoot.play();
        }

        if(shoot.getFramePosition() > 8500){
            shoot.stop();
        }

        if(teclado.d){
            angulo += Constantes.DELTAANGLE;
        }
        if(teclado.a){
            angulo -= Constantes.DELTAANGLE;
        }
        if(teclado.w){
            acceleration = heading.escalar(Constantes.acc);
            accelerating = true;
        }
        else{
            if(velocity.GetMagnitud() !=0){
                acceleration = (velocity.escalar(-1).normal()).escalar(Constantes.acc/2);
            }
            accelerating = false;
        }

        velocity = velocity.sum(acceleration);

        velocity = velocity.limite(maxVel);
        heading = heading.setDireccion(angulo - Math.PI/2);
        position = position.sum(velocity);

        if(position.getX() > Constantes.ancho){
            position.setX(0);
        }
        if(position.getY() > Constantes.alto){
            position.setY(0);
        }

        if(position.getX() < -width){
            position.setX(Constantes.ancho);
        }
        if(position.getY() < -height){
            position.setY(Constantes.alto);
        }
        if(shieldOn){
            shieldEffect.actualizar(dt);
        }

        collidesWith();


    }

    @Override
    public void Destroy(){
        spawning = true; 
        gameState.playExplosion(position);
        spawnTime = 0; 
        loose.play();
        if(!gameState.substractLife(position)){
            gameState.gameOver();
            super.Destroy();
        }
        resetValues();
    }

    private void resetValues(){

        angulo = 0; 
        velocity = new vector2D();
        position = gameState.PLAYER_START_POSITION;
    }

    @Override
    public void draw(Graphics g){

        if(!visible){
            return; 
        }
        Graphics2D g2d = (Graphics2D)g;

        AffineTransform at1 = AffineTransform.getTranslateInstance(position.getX() + width/2 + 5, position.getY() + height/2 + 10);
        AffineTransform at2 = AffineTransform.getTranslateInstance(position.getX() + 5, position.getY() + height/2 + 10);

        at1.rotate(angulo, -5, -10);
        at2.rotate(angulo, width/2 -5, -10);

        if(accelerating){
            g2d.drawImage(Assets.velocidad, at1, null);
            g2d.drawImage(Assets.velocidad, at2, null);
        }

        if(shieldOn){
            BufferedImage currentFrame = shieldEffect.getCurrentFrame();
            AffineTransform at3 = AffineTransform.getTranslateInstance(
                    position.getX() - currentFrame.getWidth()/2 + width/2,
                    position.getY() - currentFrame.getHeight() /2 + height /2);
            
                    at3.rotate(angulo, currentFrame.getWidth() /2, currentFrame.getHeight() / 2);
                    g2d.drawImage(shieldEffect.getCurrentFrame(), at3, null);
        }

        at = AffineTransform.getTranslateInstance(position.getX(), position.getY());

        at.rotate(angulo, width/2, height/2);

        if(doubleGunOn){
            g2d.drawImage(Assets.dobleArmaJugador, at, null);

        }
        else {
            g2d.drawImage(texture, at, null);
        }
    }




    
}

package org.newdawn.spaceinvaders;

import poder.TipoPoder;
import saving.Caretaker;
import saving.Estado;
import nivel.fabrica.concreto.*;
import nivel.gestor.FabricaDeNiveles;
import poder.componente_Concreto.PoderConcreto;
import poder.decorador_concreto.DisparoRapido;
import poder.decorador_concreto.DobleDisparo;
import poder.decorador_concreto.MasDanno;
import poder.decorador_concreto.Ninguno;

import java.applet.AudioClip;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Random;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Game extends Canvas {
    private BufferStrategy strategy;
    private boolean gameRunning = true;
    private ArrayList entities = new ArrayList();
    private ArrayList removeList = new ArrayList();
    private Entity ship;
    private double moveSpeed = 300;
    private long lastFire = 0;
    private int alienCount;
    private String message = "";
    private boolean waitingForKeyPress = true;
    private boolean leftPressed = false;
    private boolean rightPressed = false;
    private boolean firePressed = false;
    private boolean logicRequiredThisLoop = false;
    private boolean logicRequiredThisLoopPower = false;
    private Estado estado = new Estado(this);
    private Caretaker vigilador = new Caretaker();
    public Estado getEstado() {
        return estado;
    }

    public Game() {
        JFrame container = new JFrame("Examen 2 Kamil Sauma");
        JPanel panel = (JPanel) container.getContentPane();
        panel.setPreferredSize(new Dimension(800, 600));
        panel.setLayout(null);
        setBounds(0, 0, 800, 600);
        panel.add(this);
        setIgnoreRepaint(true);
        container.pack();
        container.setResizable(false);
        container.setVisible(true);
        container.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        addKeyListener(new KeyInputHandler());
        requestFocus();

        createBufferStrategy(2);
        strategy = getBufferStrategy();

        initEntities();
    }

    private void startGame() {
        entities.clear();
        initEntities();
		estado.setPerdio(false);

        leftPressed = false;
        rightPressed = false;
        firePressed = false;

    }

    private void initEntities() {
        ship = new ShipEntity(this, "sprites/ship1.png", 370, 550);
        ((ShipEntity) ship).setPder(estado.getPoderActual());
        entities.add(ship);
        ArrayList<Entity> temp = estado.getNivelActual().initEntities();
        alienCount = temp.size();
        entities.addAll(temp);
    }

    public void powerUp(){

        switch (new Random().nextInt(3)){
            case 0:
                ((ShipEntity) ship).setPder(new DisparoRapido(new PoderConcreto("Disparo Rápido")));
                break;
            case 1:
                ((ShipEntity) ship).setPder(new DobleDisparo(new PoderConcreto("Doble Disparo")));
                break;
            case 2:
                ((ShipEntity) ship).setPder(new MasDanno(new PoderConcreto("Más Daño")));
                break;
        }
        estado.setPoderActual(((ShipEntity) ship).getPder());
        if(estado.getPoderActual().getTipo() == TipoPoder.DISPARORAPIDO) estado.musica();
    }

    public boolean existePowerUp(){
        for(Object e : entities){
            if(e instanceof PowerUpEntity){
                return true;
            }
        }
        return false;
    }

    public void updateLogic() {
        logicRequiredThisLoop = true;
    }

    public void updatePowerLogic() {
        logicRequiredThisLoopPower = true;
    }

    public void removeEntity(Entity entity) {
        removeList.add(entity);
    }

    public void notifyDeath() {
        if(estado.getVidas()<=0){
            message = "Has perdido el juego, reiniciar denuevo?";
        }else{
            message = "Has perdido el nivel, intentar denuevo?";
        }
        waitingForKeyPress = true;
    }

    public void perdio() {
        estado.musica();
        estado.setMemento(vigilador.getMemento());
        estado.setPerdio(true);
        estado.setVidas(estado.getVidas()-1);
        vigilador.setMemento(estado.createMemento());
        if(estado.getVidas()<=0){
			estado.setNivelActual(new FabricaSeleccionarDificutlad().generarNivel(this));
			estado.setNivel(0);
			estado.setScore(0);
		}
    }

    public void notifyWin() {
        if(estado.getNivel()==5){
            message = "¡Bien hecho, has ganado el juego!";
            estado.setNivelActual(new FabricaSeleccionarDificutlad().generarNivel(this));
            estado.setNivel(0);
            estado.setScore(0);
        }else{
            estado.setNivel(estado.getNivel()+1);
            if(estado.getNivel() == 6){
                message = "¡Bien hecho, has ganado! \n Siguiente nivel:";
            }else{
                message = "Siguiente nivel:";
            }
        }
        estado.getSiguienteNivel();
        vigilador.setMemento(estado.createMemento());
        waitingForKeyPress = true;
    }

    public void notifyAlienKilled() {
        if(!existePowerUp()){
            if(estado.getContPoder()==10){
                estado.setContPoder(0);
                ((ShipEntity) ship).setPder(new Ninguno(new PoderConcreto("Ninguno")));
                estado.setPoderActual(((ShipEntity) ship).getPder());
                estado.setFiringInterval(250);
            }else{
                estado.setContPoder(estado.getContPoder()+1);
            }
        }
        alienCount = 0;
        for(Object ent : entities){
            if(ent instanceof AlienEntity){
                if(((AlienEntity) ent).getVida()>0){
                    alienCount++;
                }
            }
        }
        estado.setScore(estado.getScore()+100);
        if (alienCount == 0) {
            estado.setScore(estado.getScore()+1000);
            notifyWin();
        }

        for (int i = 0; i < entities.size(); i++) {
            Entity entity = (Entity) entities.get(i);

            if (entity instanceof AlienEntity) {
                switch (estado.getDificultad()) {
                    case FACIL:
                        entity.setHorizontalMovement(entity.getHorizontalMovement() * 1.01);
                        break;
                    case INTERMEDIO:
                        entity.setHorizontalMovement(entity.getHorizontalMovement() * 1.03);
                        break;
                    case MASTER:
                        entity.setHorizontalMovement(entity.getHorizontalMovement() * 1.05);
                        break;
                }
            }
        }
    }

    public void tryToFire() {
        // check that we have waiting long enough to fire
        if (System.currentTimeMillis() - lastFire < estado.getFiringInterval()) {
            return;
        }

        // if we waited long enough, create the shot entity, and record the time.
        lastFire = System.currentTimeMillis();
        ShotEntity shot;
        ShipEntity shipM = ((ShipEntity) ship);

        switch (shipM.getPder().getTipo()){
            case DISPARORAPIDO:
                shot = new ShotEntity(this, shipM.getPder().getTipo().getBala(), ship.getX() + 10, ship.getY() - 30);
                estado.setFiringInterval(100);
                break;
            case DOBLEDISPARO:
                shot = new ShotEntity(this, shipM.getPder().getTipo().getBala(), ship.getX() -10, ship.getY() - 30);
                ShotEntity shot2 = new ShotEntity(this, shipM.getPder().getTipo().getBala(), ship.getX() + 20, ship.getY() - 30);
                entities.add(shot2);
                break;
            case MASDANNO:
                shot = new ShotEntity(200,this, shipM.getPder().getTipo().getBala(), ship.getX() + 10, ship.getY() - 30);
                break;
            default:
                shot = new ShotEntity(this, shipM.getPder().getTipo().getBala(), ship.getX() + 10, ship.getY() - 30);
                break;
        }

        shipM.setRef(shipM.getPder().getTipo().getNave());
        entities.add(shot);
    }

    public void gameLoop() {
        long lastLoopTime = System.currentTimeMillis();
        while (gameRunning) {


            if(estado.getContPoder()==10){
                estado.setContPoder(0);
                estado.setFiringInterval(250);
                boolean llamar = false;
                if(estado.getPoderActual().getTipo() == TipoPoder.DISPARORAPIDO) llamar = true;
                ((ShipEntity) ship).setPder(new Ninguno(new PoderConcreto("Ninguno")));
                estado.setPoderActual(((ShipEntity) ship).getPder());
                entities.add(new PowerUpEntity(this, "sprites/powerup.png", Math.random()>0.5?0:800, 50));
                if(llamar) estado.musica();
            }
            ship.setRef(((ShipEntity) ship).getPder().getTipo().getNave());


            long delta = System.currentTimeMillis() - lastLoopTime;
            lastLoopTime = System.currentTimeMillis();

            Graphics2D g = (Graphics2D) strategy.getDrawGraphics();
            g.setColor(Color.black);
            g.fillRect(0, 0, 800, 600);


                for (int i = 0; i < entities.size(); i++) {
                    Entity entity = (Entity) entities.get(i);
                    if (!waitingForKeyPress){
                        entity.move(delta);
                    }
                    entity.draw(g);
                }

            for (int p = 0; p < entities.size(); p++) {
                for (int s = p + 1; s < entities.size(); s++) {
                    Entity me = (Entity) entities.get(p);
                    Entity him = (Entity) entities.get(s);

                    if (me.collidesWith(him)) {
                        me.collidedWith(him);
                        him.collidedWith(me);
                    }
                }
            }

            entities.removeAll(removeList);
            removeList.clear();

            if (logicRequiredThisLoop) {
                for (int i = 0; i < entities.size(); i++) {
                    Entity entity = (Entity) entities.get(i);
                    if(!(entity instanceof PowerUpEntity)) entity.doLogic();
                }

                logicRequiredThisLoop = false;
            }

            if (logicRequiredThisLoopPower) {
                for (int i = 0; i < entities.size(); i++) {
                    Entity entity = (Entity) entities.get(i);
                    if(entity instanceof PowerUpEntity) entity.doLogic();
                }
                logicRequiredThisLoopPower = false;
            }


            g.setColor(Color.MAGENTA);
            g.drawString(estado.getNivelActual().obtenerInformacion(), 0, 15);
            if(estado.getDificultad()!=null) {
                g.drawString("Dificultad: " + estado.getDificultad().toString(), 0, 45);
            }

            if(estado.getPoderActual().getTipo()!=TipoPoder.NINGUNO){
                g.setColor(Color.GREEN);
                g.drawString("Poder activo: " + estado.getPoderActual().getNombre(), (800 - g.getFontMetrics().stringWidth("Poder activo: " + estado.getPoderActual().getNombre())) / 2, 25);
            }

            g.setColor(Color.ORANGE);
            g.drawString("Score: " + estado.getScore(), (getWidth() - g.getFontMetrics().stringWidth("Score: " + estado.getScore())), getHeight());
            g.drawString("Vidas: " + estado.getVidas(), 0, getHeight());

            if (waitingForKeyPress) {
                g.setColor(Color.white);
                g.drawString(message, (800 - g.getFontMetrics().stringWidth(message)) / 2, 250);
                g.drawString(FabricaDeNiveles.CrearFabricaDeNiveles(estado.getNivelActual()), (800 - g.getFontMetrics().stringWidth(FabricaDeNiveles.CrearFabricaDeNiveles(estado.getNivelActual()))) / 2, 270);
                g.drawString("Presione cualquier tecla", (800 - g.getFontMetrics().stringWidth("Presione cualquier tecla")) / 2, 300);
            }

            if (estado.getNivel() == 0) {
                g.setColor(Color.white);
                g.drawString("Fácil", 210, 40);
                g.drawString("Intermedio", 345, 40);
                g.drawString("Master", 503, 40);
            }

            g.dispose();
            strategy.show();

            ship.setHorizontalMovement(0);

            if ((leftPressed) && (!rightPressed)) {
                ship.setHorizontalMovement(-moveSpeed);
            } else if ((rightPressed) && (!leftPressed)) {
                ship.setHorizontalMovement(moveSpeed);
            }

            if (firePressed) {
                tryToFire();
            }
            try {
                Thread.sleep(10);
            } catch (Exception e) {
            }
        }
    }

    private class KeyInputHandler extends KeyAdapter {
        /**
         * The number of key presses we've had while waiting for an "any key" press
         */
        private int pressCount = 1;

        /**
         * Notification from AWT that a key has been pressed. Note that
         * a key being pressed is equal to being pushed down but *NOT*
         * released. Thats where keyTyped() comes in.
         *
         * @param e The details of the key that was pressed
         */
        public void keyPressed(KeyEvent e) {
            // if we're waiting for an "any key" typed then we don't
            // want to do anything with just a "press"
            if (waitingForKeyPress) {
                return;
            }


            if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                leftPressed = true;
            }
            if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                rightPressed = true;
            }
            if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                firePressed = true;
            }
        }

        /**
         * Notification from AWT that a key has been released.
         *
         * @param e The details of the key that was released
         */
        public void keyReleased(KeyEvent e) {
            // if we're waiting for an "any key" typed then we don't
            // want to do anything with just a "released"
            if (waitingForKeyPress) {
                return;
            }

            if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                leftPressed = false;
            }
            if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                rightPressed = false;
            }
            if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                firePressed = false;
            }
        }

        /**
         * Notification from AWT that a key has been typed. Note that
         * typing a key means to both press and then release it.
         *
         * @param e The details of the key that was typed.
         */
        public void keyTyped(KeyEvent e) {
            // if we're waiting for a "any key" type then
            // check if we've recieved any recently. We may
            // have had a keyType() event from the user releasing
            // the shoot or move keys, hence the use of the "pressCount"
            // counter.
            if (waitingForKeyPress) {
                if (pressCount == 1) {
                    // since we've now recieved our key typed
                    // event we can mark it as such and start
                    // our new game
                    waitingForKeyPress = false;
                    startGame();
                    pressCount = 0;
                } else {
                    pressCount++;
                }
            }

            // if we hit escape, then quit the game
            if (e.getKeyChar() == 27) {
                System.exit(0);
            }
        }
    }
    public static void main(String argv[]) {
        Game g = new Game();
        g.gameLoop();
    }
}

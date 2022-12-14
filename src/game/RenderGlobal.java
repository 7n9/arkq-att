package game;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.*;
import org.newdawn.slick.font.effects.ColorEffect;

import static org.lwjgl.opengl.GL11.*;

public class RenderGlobal {

    public RenderGlobal(){
        initFont(oldFontSize);

    }
    public void drawFilledRect(float x, float y, float width, float height) {
        glPushMatrix();

        glTranslatef(x, y, 0);
        glBegin(GL_QUADS);
        glVertex2f(0, 0);
        glVertex2f(0, height);
        glVertex2f(width, height);
        glVertex2f(width, 0);
        glEnd();
        glPopMatrix();
    }

    public void drawFilledRectWithColor(float x, float y, float width, float height, int color) {

        float alpha = ((color >> 24) & 0xff) / 255F;
        float red = ((color >> 16) & 0xff) / 255F;
        float green = ((color >> 8) & 0xff) / 255F;
        float blue = (color & 0xff) / 255F;

        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glColor4f(red, green, blue, alpha);
        drawFilledRect( x, y, width, height);
        glDisable(GL_BLEND);
    }


    public void drawHollowRect(float x, float y, float width, float height, float thickness){
        glPushMatrix();

        glTranslatef(x - 0.5f, y - 0.5f, 0);//shift to hit pixel
        glBegin(GL_LINE_LOOP);
        glLineWidth(thickness);
        glVertex2f(0, 0);
        glVertex2f(0, height);
        glVertex2f(width, height);
        glVertex2f(width, 0);
        glEnd();

        glPopMatrix();
    }

    public void drawHollowRectWithColor(float x, float y, float width, float height, float thickness, int color){
        float alpha = ((color >> 24) & 0xff) / 255F;
        float red = ((color >> 16) & 0xff) / 255F;
        float green = ((color >> 8) & 0xff) / 255F;
        float blue = (color & 0xff) / 255F;

        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glColor4f(red, green, blue, alpha);
        glDisable(GL_BLEND);

        drawHollowRect(x, y, width, height, thickness);
    }



    public void drawCircle(double x, double y, double radius, int color) {
        // 0xAARRGGBB
        float alpha = ((color >> 24) & 0xff) / 255F;
        float red = ((color >> 16) & 0xff) / 255F;
        float green = ((color >> 8) & 0xff) / 255F;
        float blue = (color & 0xff) / 255F;
        glEnable(GL11.GL_BLEND);
        glDisable(GL11.GL_TEXTURE_2D);
        glEnable(GL11.GL_POLYGON_SMOOTH);
        glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        glHint(GL11.GL_POLYGON_SMOOTH_HINT, GL11.GL_NICEST);
        glColor4f(red, green, blue, alpha);
        glBegin(GL_POLYGON);
        for (int i = 0; i <= 360; i++) {
            double x2 = Math.sin(((i * 3.141526D) / 180)) * radius;
            double y2 = Math.cos(((i * 3.141526D) / 180)) * radius;
            glVertex3d(x+x2, y+y2, 0);
        }
        glEnd();
        glDisable(GL_POLYGON_SMOOTH);
        glEnable(GL_TEXTURE_2D);
        glDisable(GL_BLEND);
    }


    public void initFont(int size){
        font = null;
        try {
            font = new UnicodeFont("src/game/font/Vogue.ttf", size, false, false);
            font.getEffects().add(new ColorEffect(java.awt.Color.white)); // set the default color to white
            font.addAsciiGlyphs();
            font.loadGlyphs(); // load glyphs from font file
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }

    public void drawFont(float posx, float posy, int size, String text, Color color){

        if(size != oldFontSize){
            initFont(size);
        }

        glPushMatrix();
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glDisable(GL_DEPTH_TEST);
        glEnable(GL_TEXTURE_2D);

        font.drawString(posx, posy, text, color);

        glDisable(GL_TEXTURE_2D);
        glDisable(GL_DEPTH_TEST);
        glDisable(GL_BLEND);
        glPopMatrix();
    }

    private UnicodeFont font;
    private int oldFontSize = 30;
}

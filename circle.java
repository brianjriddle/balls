import java.applet.Applet;
import java.awt.*;
import java.io.PrintStream;
import java.util.Random;

public class circle extends Applet
    implements Runnable
{
    public class ball
    {

        public void moveball()
        {
            if(xdir == 0)
                x += xinc;
            else
                x -= xinc;
            if(ydir == 0)
                y += yinc;
            else
                y -= yinc;
            if(x >= appwidth - (circlewidth + inc))
            {
                xdir = 1;
                xinc = randinc(inc) + 1;
                x -= xinc;
            }
            if(x <= inc)
            {
                xdir = 0;
                xinc = randinc(inc) + 1;
                x += xinc;
            }
            if(y >= appheight - (circleheight + inc))
            {
                ydir = 1;
                yinc = randinc(inc) + 1;
                y -= yinc;
            }
            if(y <= inc)
            {
                ydir = 0;
                yinc = randinc(inc) + 1;
                y += yinc;
            }
        }

        public int randinc(int i)
        {
            return Math.abs(rand.nextInt() % i);
        }

        int x;
        int y;
        int circlewidth;
        int circleheight;
        int xdir;
        int ydir;
        int xinc;
        int yinc;
        int red;
        int green;
        int blue;
        Color ballcolor;
        Random rand;

        public ball()
        {
            rand = new Random(System.currentTimeMillis());
            x = randinc(appwidth);
            y = randinc(appheight);
            circlewidth = randinc(circle.girth) + 10;
            circleheight = circlewidth;
            xdir = randinc(2);
            ydir = randinc(2);
            xinc = randinc(inc) + 1;
            yinc = randinc(inc) + 1;
            red = randinc(circle.color);
            green = randinc(circle.color);
            blue = randinc(circle.color);
            ballcolor = new Color(red, green, blue);
        }
    }


    public circle()
    {
        inc = 17;
    }

    public void init()
    {
        if(appwidth == 0)
        {
            appwidth = 400;
            appheight = 400;
        } else
        {
            appwidth = 300;
            appheight = 300;
        }
        marbles = new ball[ballcount];
        for(int i = 0; i < ballcount; i++)
        {
            marbles[i] = new ball();
            try
            {
                Thread.sleep(100L);
            }
            catch(Exception _ex)
            {
                System.out.println("main sleep problem");
            }
        }

        offscreenimage = createImage(appwidth, appheight);
    }

    public synchronized void paint(Graphics g)
    {
        g.setXORMode(getBackground());
        for(int i = 0; i < ballcount; i++)
            marbles[i].moveball();

        for(int j = 0; j < ballcount; j++)
        {
            g.setColor(marbles[j].ballcolor);
            g.fillOval(marbles[j].x, marbles[j].y, marbles[j].circlewidth, marbles[j].circleheight);
        }

    }

    public void run()
    {
        while(bouncer != null) 
        {
            try
            {
                Thread.sleep(100L);
            }
            catch(Exception _ex)
            {
                System.out.println("Sleep problem");
            }
            repaint();
        }
    }

    public void start()
    {
        if(bouncer == null)
        {
            bouncer = new Thread(this);
            bouncer.start();
        }
    }

    public void stop()
    {
        bouncer = null;
    }

    public void update(Graphics g)
    {
        offscreenGraphics = offscreenimage.getGraphics();
        offscreenGraphics.setColor(getBackground());
        offscreenGraphics.fillRect(0, 0, appwidth, appheight);
        offscreenGraphics.setColor(g.getColor());
        paint(offscreenGraphics);
        g.drawImage(offscreenimage, 0, 0, this);
    }

    Thread bouncer;
    ball marbles[];
    Image offscreenimage;
    Graphics offscreenGraphics;
    int inc;
    static int color = 256;
    static int girth = 65;
    static int tall = 65;
    static int ballcount = 30;
    int appwidth;
    int appheight;

}

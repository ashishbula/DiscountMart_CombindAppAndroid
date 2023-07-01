package in.discountmart.business.bus_captcha;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;

import java.io.CharArrayWriter;
import java.util.ArrayList;
import java.util.Random;

public class TextCaptcha extends Captcha {

protected TextOptions options;
private int wordLength;
public char mCh;

	public enum TextOptions{
	    UPPERCASE_ONLY,
	    LOWERCASE_ONLY,
	    NUMBERS_ONLY,
	    LETTERS_ONLY,
	    NUMBERS_AND_LETTERS
	}

	public TextCaptcha(int wordLength, TextOptions opt){
		 new TextCaptcha(0, 0, wordLength, opt);
	}

	public TextCaptcha(int width, int height, int wordLength, TextOptions opt){
	    setHeight(height);
	    setWidth(width);
	    this.options = opt;
	    usedColors = new ArrayList<Integer>();
	    this.wordLength = wordLength;
	    this.image = image();
	}

	@Override
	protected Bitmap image() {
	    LinearGradient gradient = new LinearGradient(0, 0, getWidth() / this.wordLength, getHeight() / 2, color(), color(), Shader.TileMode.MIRROR);
	    Paint p = new Paint();
	    p.setDither(true);
	    //p.setShader(gradient);
	    Bitmap bitmap = Bitmap.createBitmap(getWidth(), getHeight(),Config.RGB_565);
	    Canvas c = new Canvas(bitmap);
	    c.drawRect(0, 0, getWidth(), getHeight(), p);
	    Paint tp = new Paint();
	    tp.setDither(true);
	    tp.setTextSize(getWidth() / getHeight() * 8);

	    Random r = new Random(System.currentTimeMillis());
	    CharArrayWriter cab = new CharArrayWriter();
	    this.answer = "";
	    for(int i = 0; i < this.wordLength; i ++){
	        char ch = ' ';
	        switch (options) {
	        case UPPERCASE_ONLY:
	            ch = (char) (r.nextInt(91 - 65) + (65));
	            break;
	        case LOWERCASE_ONLY:
	            ch = (char) (r.nextInt(123 - 97) + (97));
	            break;
	        case NUMBERS_ONLY:
	            ch = (char) (r.nextInt(58 - 49) + (49));
	            break;
	        case LETTERS_ONLY:
	            ch = getLetters(r);
	            break;
	        case NUMBERS_AND_LETTERS:
	            ch = getLettersNumbers(r);
	            break;
	        default:
	            ch = getLettersNumbers(r);
	            break;
	        }
	        cab.append(ch);
	        this.answer += ch;
	    }

	    char[] data = cab.toCharArray();
	    for (int i=0; i<data.length; i++) {
	       // this.x += ( 30- (2* this.wordLength)) + (Math.abs(r.nextInt()) % (65 - (1.2 * this.wordLength)));
	        //this.y = 50 + Math.abs(r.nextInt()) % 50;

			 this.x +=  15 + (Math.abs(r.nextInt()) % 15);
			this.y = 20 + Math.abs(r.nextInt()) % 20;
	        Canvas cc = new Canvas(bitmap);
	       // tp.setTextSkewX(r.nextFloat() - r.nextFloat());
	        tp.setColor(Color.WHITE);
	        cc.drawText(data, i, 1, this.x, this.y, tp);
	        tp.setTextSkewX(0);
	    }
	    return bitmap;
	}

	private char getLetters(Random r){
	    int rint = (r.nextInt(123 - 65) + (65));
	    if(((rint > 90) && (rint < 97)))
	        return getLetters(r);
	    else
	     return    mCh = (char) rint;
		//return 0;
	}

	private char getLettersNumbers(Random r) {
	    int rint = (r.nextInt(123 - 49) + (49));

	    if(((rint > 90) && (rint < 97)))
			return getLettersNumbers(r);
	    else if(((rint > 57) && (rint < 65)))
			return getLettersNumbers(r);
	    else
			return mCh = (char) rint;
		//return 0;
	}
}

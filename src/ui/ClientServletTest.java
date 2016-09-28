package ui;

import org.junit.Before;
import org.junit.Test;

import javax.servlet.http.Cookie;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by o_0 on 2016-09-28.
 */
public class ClientServletTest {
    public Collection<Cookie> testCookies;
    public String lastValueInCart = "234";

    @Before
    public void setUp() throws Exception {
        testCookies = new ArrayList<>();
        Cookie c;
        for (int i=0;i<10;i++){
            if (i == 0){
                c = new Cookie("shoppingcart","34:56:232:" + lastValueInCart);

            }else {
                c = new Cookie("nummer"+i,""+i);
            }
            testCookies.add(c);
        }
    }

    @Test
    public void TestClientservlet() {
        ClientServlet servlet = new ClientServlet();
        assertTrue(servlet != null);
    }


    @Test
    public void TestParseCookie() {
        ClientServlet servlet = new ClientServlet();
        ArrayList<Integer> controll = new ArrayList<>();
        String value = "0";
        controll.add(0);
        for (int i = 1; i < 10; i++) {
            controll.add(i);
            value += ":" + i;
        }
        Cookie shoppingCart = new Cookie("shoppingcart", value);

        Collection<Integer> result = servlet.parseShoppingCartCookie(shoppingCart);
        assertTrue("Size mismatch:result = " + result.size() +" control:" +controll.size(),result.size() == controll.size());
        int idx = 0;
        boolean flag = true;
        for(Integer id : result) {
            if (id != controll.get(idx)) {
                flag = false;
                break;
            }
            idx++;
        }

        assertTrue("Parse failed",flag);
    }

    @Test
    public void TestWrongDelimiter() {
        ClientServlet servlet = new ClientServlet();
        Cookie shoppingcart = new Cookie("shoppingcart", "34:56,232:" + lastValueInCart);
        List<Integer> result  = (List<Integer>) servlet.parseShoppingCartCookie(shoppingcart);
        assertTrue("Did not handle wrong delimeter",result.size() == 2);
    }

    @Test
    public void TestParseCookieArray() {
        ClientServlet servlet = new ClientServlet();
        System.out.println(testCookies);
        List<Integer> result  = (List<Integer>) servlet.parseShoppingCartCookie(testCookies.toArray(new Cookie[testCookies.size()]));
        assertTrue("No results:",result.size() > 0);
        assertTrue("Fail to parse cookie array",result.get(result.size()-1).toString().equals(lastValueInCart));
    }

    @Test
    public void TestAddProductToCartCookie() {
        String testValue = "23";
        ClientServlet servlet = new ClientServlet();

        Cookie cookie = servlet.addProductToCartCookie(testCookies.toArray(new Cookie[testCookies.size()]), testValue);
        List<Integer> result  = (List<Integer>) servlet.parseShoppingCartCookie(cookie);
        assertTrue("Fail to add value to cartCookie",result.get(result.size()-1).toString().equals(testValue));
    }
}
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class main {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        HelloBean helloBean = (HelloBean) context.getBean("helloBean");

        System.out.println(helloBean.getMessage());
    }
}

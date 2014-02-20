package pl.piotrsukiennik.tuner.xml.context;

/**
 * @author Piotr Sukiennik
 * @date 20.02.14
 */
public class WrapperContextBeanDefinitionParser extends ContextExtenderBeanDefinitionParser {

    private String[] resources = { "classpath*:jsqltuner-wrapper-context.xml" };

    @Override
    protected String[] getResources() {
        return resources;
    }
}

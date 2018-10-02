package com.epam.spring.spittrMvc.factories;

import com.epam.spring.spittrMvc.beans.AsyncCommand;
import com.epam.spring.spittrMvc.beans.CommandManager;
import com.epam.spring.spittrMvc.beans.TestBean;
import com.epam.spring.spittrMvc.interfaces.Command;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Component
public class FactoryMethodComponent {

    private static int i;

    @Bean
    @Qualifier("public")
    public TestBean publicInstance() {
        return new TestBean("publicInstance");
    }

    // use of a custom qualifier and autowiring of method parameters
    @Bean
    protected TestBean protectedInstance(
            @Qualifier("public") TestBean spouse,
            @Value("${privateInstance.age}") String country) {
        TestBean tb = new TestBean("protectedInstance");
        tb.setSpouse(spouse);
        tb.setCountry(country);
        return tb;
    }

    @Bean
    @Scope("prototype")
    public AsyncCommand asyncCommand() {
        AsyncCommand command = new AsyncCommand();
        // inject dependencies here as required
        return command;
    }

    @Bean
    public CommandManager commandManager() {
        // return new anonymous implementation of CommandManager with command() overridden
        // to return a new prototype Command object
        return new CommandManager() {
            protected Command createCommand() {
                return asyncCommand();
            }
        };
    }

    @Bean
    private TestBean privateInstance() {
        return new TestBean("privateInstance");
    }

    @Bean
    @RequestScope
    public TestBean requestScopedInstance() {
        return new TestBean("requestScopedInstance");
    }
}

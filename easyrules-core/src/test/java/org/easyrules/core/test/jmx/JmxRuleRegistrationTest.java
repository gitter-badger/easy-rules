package org.easyrules.core.test.jmx;

import org.easyrules.api.Rule;
import org.easyrules.api.RulesEngine;
import org.easyrules.core.BasicRule;
import org.easyrules.core.DefaultRulesEngine;
import org.junit.Before;
import org.junit.Test;

import javax.management.*;

import java.lang.management.ManagementFactory;

import static org.junit.Assert.*;

/**
 * Test class for JMX managed rule registration.
 *
 * @author Mahmoud Ben Hassine (md.benhassine@gmail.com)
 */
public class JmxRuleRegistrationTest {

    private Rule rule;

    private RulesEngine<Rule> rulesEngine;

    @Before
    public void setup(){

        rule = new BasicRule();

        rulesEngine = new DefaultRulesEngine();
    }

    @Test
    public void testJmxRuleRegistration() throws MalformedObjectNameException, IntrospectionException, InstanceNotFoundException, ReflectionException {

        rulesEngine.registerJmxRule(rule);

        //assert that the rule has been successfully registered within the JMX registry
        MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
        ObjectName name = new ObjectName("org.easyrules.core.jmx:type=" + rule.getClass().getSimpleName() + ",name=" + rule.getName());
        MBeanInfo mBeanInfo = mBeanServer.getMBeanInfo(name);
        assertNotNull(mBeanInfo);
        assertTrue(mBeanServer.isRegistered(name));

    }

    @Test
    public void testJmxRuleUnregistration() throws MalformedObjectNameException, IntrospectionException, InstanceNotFoundException, ReflectionException {

        rulesEngine.unregisterJmxRule(rule);

        //assert that the rule has been successfully unregistered form the JMX registry
        MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
        ObjectName name = new ObjectName("org.easyrules.core.jmx:type=" + rule.getClass().getSimpleName() + ",name=" + rule.getName());
        assertFalse(mBeanServer.isRegistered(name));

    }

}

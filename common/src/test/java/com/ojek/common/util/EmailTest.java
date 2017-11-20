package com.ojek.common.util;

import org.junit.Test;

import static com.ojek.common.util.StringUtil.validateEmail;
import static junit.framework.TestCase.assertTrue;

public class EmailTest {

    @Test
    public void testEmail() {
        assertTrue(validateEmail("jauhar@gmail.com"));
    }

}

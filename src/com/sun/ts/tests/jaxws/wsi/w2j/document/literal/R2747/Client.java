/*
 * Copyright (c) 2007, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0, which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * This Source Code may also be made available under the following Secondary
 * Licenses when the conditions for such availability set forth in the
 * Eclipse Public License v. 2.0 are satisfied: GNU General Public License,
 * version 2 with the GNU Classpath Exception, which is available at
 * https://www.gnu.org/software/classpath/license.html.
 *
 * SPDX-License-Identifier: EPL-2.0 OR GPL-2.0 WITH Classpath-exception-2.0
 */

/*
 * $Id$
 */

package com.sun.ts.tests.jaxws.wsi.w2j.document.literal.R2747;

import java.util.Properties;

import javax.xml.ws.*;
import com.sun.javatest.Status;
import com.sun.ts.lib.harness.*;

import com.sun.ts.tests.jaxws.sharedclients.ClientFactory;

public class Client extends ServiceEETest {
  /**
   * The one string to be echoed.
   */
  private static final String STRING_1 = "R2747-1";

  /**
   * The other string to be echoed.
   */
  private static final String STRING_2 = "R2747-2";

  /**
   * The one client.
   */
  private W2JDLR2747ClientOne client1;

  /**
   * The other client.
   */
  private W2JDLR2747ClientTwo client2;

  static W2JDLR2747TestService service = null;

  /**
   * Test entry point.
   * 
   * @param args
   *          the command-line arguments.
   */
  public static void main(String[] args) {
    Client client = new Client();
    Status status = client.run(args, System.out, System.err);
    status.exit();
  }

  /**
   * @class.testArgs: -ap jaxws-url-props.dat
   * @class.setup_props: webServerHost; webServerPort; platform.mode;
   *
   * @param args
   * @param properties
   *
   * @throws Fault
   */
  public void setup(String[] args, Properties properties) throws Fault {
    client1 = (W2JDLR2747ClientOne) ClientFactory
        .getClient(W2JDLR2747ClientOne.class, properties, this, service);
    client2 = (W2JDLR2747ClientTwo) ClientFactory
        .getClient(W2JDLR2747ClientTwo.class, properties, this, service);
    logMsg("setup ok");
  }

  public void cleanup() {
    logMsg("cleanup");
  }

  /**
   * @testName: testExtensionElementUnderstanding
   *
   * @assertion_ids: WSI:SPEC:R2747
   *
   * @test_Strategy: The supplied WSDL, containing two port types, which are
   *                 identical except for the use of the wsdl:required attribute
   *                 on the soap binding extension elements, has been used by
   *                 the WSDL-to-Java tool to generate an end point. If the tool
   *                 works correctly, the end-point has been built and deployed
   *                 so it should simply be reachable.
   *
   * @throws Fault
   */
  public void testExtensionElementUnderstanding() throws Fault {
    String result;
    try {
      result = client1.echoString(STRING_1);
    } catch (Exception e) {
      throw new Fault("Unable to invoke echoString operation (BP-R2747)", e);
    }
    if (!STRING_1.equals(result)) {
      throw new Fault("echoString operation returns '" + result
          + "' in stead of '" + STRING_1 + "' (BP-R2747)");
    }
    try {
      result = client2.echoString(STRING_2);
    } catch (Exception e) {
      throw new Fault("Unable to invoke echoString operation (BP-R2747)", e);
    }
    if (!STRING_2.equals(result)) {
      throw new Fault("echoString operation returns '" + result
          + "' in stead of '" + STRING_2 + "' (BP-R2747)");
    }
  }
}

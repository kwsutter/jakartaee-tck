/*
 * Copyright (c) 2007, 2018 Oracle and/or its affiliates. All rights reserved.
 * Copyright (c) 2002 International Business Machines Corp. All rights reserved.
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

package com.sun.ts.tests.webservices.deploy.multiDeploy.war;

import com.sun.ts.lib.util.*;
import com.sun.ts.lib.porting.*;
import com.sun.ts.lib.harness.*;
import com.sun.ts.tests.jaxrpc.common.*;
import com.sun.javatest.Status;

import java.util.Iterator;
import java.rmi.Remote;
import javax.xml.rpc.Service;
import javax.xml.rpc.Call;
import javax.xml.rpc.handler.HandlerRegistry;
import javax.xml.rpc.encoding.TypeMappingRegistry;
import javax.xml.rpc.ServiceException;
import javax.xml.namespace.QName;
import javax.naming.InitialContext;
import java.util.Properties;

public class Client extends EETest {
  HelloWs port1 = null;

  Service svc1 = null;

  HelloWs port2 = null;

  Service svc2 = null;

  InitialContext ctx;

  private void getStub() throws Exception {
    TestUtil.logMsg("JNDI lookup for Service1");
    ctx = new InitialContext();
    svc1 = (javax.xml.rpc.Service) ctx
        .lookup("java:comp/env/service/multiDeployWar/svc1");
    TestUtil.logMsg("Get port1 from Service1");
    port1 = (HelloWs) svc1.getPort(HelloWs.class);
    TestUtil.logMsg("Port1 obtained");

    TestUtil.logMsg("JNDI lookup for Service2");
    svc2 = (javax.xml.rpc.Service) ctx
        .lookup("java:comp/env/service/multiDeployWar/svc2");
    TestUtil.logMsg("Get port2 from Service2");
    port2 = (HelloWs) svc2.getPort(HelloWs.class);

    TestUtil.logMsg("Port2 obtained");
  }

  public static void main(String[] args) {
    Client theTests = new Client();
    Status s = theTests.run(args, System.out, System.err);
    s.exit();
  }

  /* Test setup */

  /*
   * @class.testArgs: -ap jaxrpc-url-props.dat
   * 
   * @class.setup_props: webServerHost; webServerPort;
   */

  public void setup(String[] args, Properties p) throws Fault {
    try {
      getStub();
    } catch (Exception e) {
      throw new Fault("setup failed:", e);
    }
    logMsg("setup ok");
  }

  public void cleanup() throws Fault {
    logMsg("cleanup ok");
  }

  private void printSeperationLine() {
    TestUtil.logMsg("---------------------------");
  }

  /*
   * @testName: multiDeployWarCall
   *
   * @assertion_ids: WS4EE:SPEC:185
   * 
   * @test_Strategy: The same WSDL file is deployed twice in the same module.
   * Use two different service-refs to access the two implementations, and make
   * sure the results are what's expected
   */
  public void multiDeployWarCall() throws Fault {
    TestUtil.logMsg("MultiDeployWarCall");
    try {
      String ret2 = port2.sayHello("multiDeployWar");
      String ret1 = port1.sayHello("multiDeployWar");
      if (!ret1.equals("'multiDeployWar' to you too!")) {
        TestUtil.logMsg(
            "test MultiDeployWarCall failed: return value from first implementationis: "
                + ret1);
        throw new Fault("MultiDeployWarCall failed");
      } else
        TestUtil.logMsg("first call returned expected result");

      if (!ret2.equals("'multiDeployWar' to me too!")) {
        TestUtil.logMsg(
            "test MultiDeployWarCall failed: return value from second implementation is: "
                + ret2);
        throw new Fault("MultiDeployWarCall failed");
      } else
        TestUtil.logMsg("second call returned expected result");

      TestUtil.logMsg("MultiDeployWarCall passed");
    } catch (Throwable t) {
      TestUtil.logMsg(
          "test MultiDeployWarCall failed: got exception " + t.toString());
      throw new Fault("MultiDeployWarCall failed");
    }
    return;
  }

}

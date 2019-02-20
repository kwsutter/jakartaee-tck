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

package com.sun.ts.tests.jpa.core.callback.inheritance;

import com.sun.ts.tests.jpa.core.callback.common.CallbackStatusIF;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;

@Entity
@DiscriminatorValue("PartProduct")
@EntityListeners(com.sun.ts.tests.jpa.core.callback.inheritance.PartProductListener.class)
public class PartProduct extends Product
    implements java.io.Serializable, CallbackStatusIF {
  private long partNumber;

  public PartProduct() {
    super();
  }

  @Column(name = "PNUM")
  public long getPartNumber() {
    return partNumber;
  }

  public void setPartNumber(long v) {
    this.partNumber = v;
  }
}
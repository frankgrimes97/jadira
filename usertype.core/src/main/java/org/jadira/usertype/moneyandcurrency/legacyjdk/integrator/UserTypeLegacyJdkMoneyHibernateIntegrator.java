/*
 *  Copyright 2011 Christopher Pheby
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.jadira.usertype.moneyandcurrency.legacyjdk.integrator;

import org.hibernate.integrator.spi.Integrator;
import org.hibernate.usertype.CompositeUserType;
import org.hibernate.usertype.UserType;
import org.jadira.usertype.moneyandcurrency.legacyjdk.PersistentCurrency;
import org.jadira.usertype.spi.shared.AbstractUserTypeHibernateIntegrator;

public class UserTypeLegacyJdkMoneyHibernateIntegrator extends AbstractUserTypeHibernateIntegrator implements Integrator {

	private static UserType[] userTypes;
	private static final CompositeUserType[] COMPOSITE_USER_TYPES = new CompositeUserType[]{};

	static {
			
		userTypes = new UserType[] { new PersistentCurrency() };
			
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected CompositeUserType[] getCompositeUserTypes() {
		return COMPOSITE_USER_TYPES;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected UserType[] getUserTypes() {
		return userTypes;
	}	
}

/*
 *  Copyright 2013 Christopher Pheby
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
package org.jadira.usertype.spi.shared.descriptor.sql;

import java.sql.*;
import java.util.Calendar;

import org.hibernate.type.descriptor.ValueBinder;
import org.hibernate.type.descriptor.ValueExtractor;
import org.hibernate.type.descriptor.WrapperOptions;
import org.hibernate.type.descriptor.java.JavaTypeDescriptor;
import org.hibernate.type.descriptor.sql.BasicBinder;
import org.hibernate.type.descriptor.sql.BasicExtractor;
import org.hibernate.type.descriptor.sql.SqlTypeDescriptor;
import org.hibernate.type.descriptor.sql.TimeTypeDescriptor;

public class DstSafeTimeTypeDescriptor extends TimeTypeDescriptor {

	private static final long serialVersionUID = -1927559005967709998L;

	private final Calendar cal; // calendar.getInstance(TimeZone.getTimeZone("GMT"));
	
//	public static final DstSafeTimeTypeDescriptor INSTANCE = new DstSafeTimeTypeDescriptor();

	public DstSafeTimeTypeDescriptor() {
		cal = null;
	}
	
	public DstSafeTimeTypeDescriptor(Calendar cal) {
		this.cal = cal;
	}
	
	public <X> ValueBinder<X> getBinder(
			final JavaTypeDescriptor<X> javaTypeDescriptor) {

		return new BasicBinder<X>(javaTypeDescriptor, (SqlTypeDescriptor) this) {
			@Override
			protected void doBind(PreparedStatement st, X value, int index,
					WrapperOptions options) throws SQLException {
				if (cal == null) {
					st.setTime(index,
							javaTypeDescriptor.unwrap(value, Time.class, options));
				} else {
					st.setTime(index,
							javaTypeDescriptor.unwrap(value, Time.class, options), cal);	
				}
			}
			@Override
			protected void doBind(CallableStatement st, X value, String name, WrapperOptions options) throws SQLException {
				if (cal == null) {
					st.setDate(name,
							javaTypeDescriptor.unwrap(value, Date.class, options));
				} else {
					st.setDate(name,
							javaTypeDescriptor.unwrap(value, Date.class, options), cal);
				}
			}
		};
	}

	public <X> ValueExtractor<X> getExtractor(
			final JavaTypeDescriptor<X> javaTypeDescriptor) {
		return new BasicExtractor<X>(javaTypeDescriptor, (SqlTypeDescriptor) this) {
			@Override
			protected X doExtract(ResultSet rs, String name,
					WrapperOptions options) throws SQLException {
				if (cal == null) {
					return javaTypeDescriptor.wrap(rs.getTime(name), options);
				} else {
					return javaTypeDescriptor.wrap(rs.getTime(name, cal), options);	
				}
			}
			
			protected X doExtract(CallableStatement statement, int index, WrapperOptions options) throws SQLException {
				if (cal == null) {
					return javaTypeDescriptor.wrap(statement.getTime(index), options);
				} else {
					return javaTypeDescriptor.wrap(statement.getTime(index, cal), options);	
				}
			}

			protected X doExtract(CallableStatement statement, String name, WrapperOptions options) throws SQLException {
				if (cal == null) {
					return javaTypeDescriptor.wrap(statement.getTime(name), options);
				} else {
					return javaTypeDescriptor.wrap(statement.getTime(name, cal), options);	
				}
			}
		};
	}
}

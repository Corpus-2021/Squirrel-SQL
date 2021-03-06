package net.sourceforge.squirrel_sql.client.session.parser.kernel;

/*
 * Copyright (C) 2008 Gerd Wagner
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */


public class ErrorInfo
{
	public String message;
	public int beginPos;
	public int endPos;

	public String key;

	public ErrorInfo(String message, int beginPos, int endPos)
	{
		this.message = message;
		this.beginPos = beginPos;
		this.endPos = endPos;

		key = message + "_" + beginPos + "_" + endPos;
	}


	public boolean matches(ErrorInfo other)
	{
		return key.equals(other.key);
	}
}

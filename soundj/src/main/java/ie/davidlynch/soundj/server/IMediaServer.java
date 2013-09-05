/**
 * This file is part of SounDJ.
 *
 * SounDJ is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * SounDJ is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with SounDJ.  If not, see <http://www.gnu.org/licenses/>.
 */
package ie.davidlynch.soundj.server;

/**
 * SounDJ needs to somehow accept data from external applications. This fits the client-server
 * model quite well. Implementors of this interface provide the server part of this architecture.
 *
 * @author David Lynch
 */
public interface IMediaServer 
{
	public void start() throws Exception;

	public void stop();
	
	public void join();
}

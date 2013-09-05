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
package ie.davidlynch.soundj;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import ie.davidlynch.soundj.model.Play;
import ie.davidlynch.soundj.server.IMediaServer;
import ie.davidlynch.soundj.server.icecast.IceCastMediaServer;

public class SounDJ 
{
   public static void main(String[] list) throws Exception 
   {
	   BlockingQueue<Play> playsQueue = new LinkedBlockingQueue<Play>();
	   IMediaServer server = new IceCastMediaServer(8000, playsQueue);
	   server.start();
	   server.join();
   }
}


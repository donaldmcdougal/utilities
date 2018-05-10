/**
 * 
 */
package com.schneider.utils.net;

import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * Provides networking utilities.
 * @author Donald McDougal
 *
 */
public class NetUtils {
	
	/**
	 * The timeout to use for determining whether or not a network address is reachable.
	 */
	public static int TIMEOUT = 30000;
	
	/**
	 * A convenience method for setting the timeout for reaching InetAddress objects.
	 * The default is 30 seconds.
	 * @param timeout The timeout to set.
	 */
	public static void setTimeout(int timeout) {
		TIMEOUT = timeout;
	}

	/**
	 * Gets all network addresses.
	 * @return All network addresses on the local machine.
	 * @throws SocketException
	 */
	public static List<InetAddress> getAllNetworkAddresses() throws SocketException {
		Enumeration<NetworkInterface> nfaces = NetworkInterface.getNetworkInterfaces();
		List<InetAddress> addrs = new ArrayList<InetAddress>();
		while (nfaces.hasMoreElements()) {
			NetworkInterface nface = nfaces.nextElement();
			Enumeration<InetAddress> ins = nface.getInetAddresses();
			while (ins.hasMoreElements()) {
				addrs.add(ins.nextElement());
			}
		}
		return addrs;
	}
	
	/**
	 * Guesses the network address of the machine.  Designed to work with addresses that
	 * are behind a router.
	 * @return The first recognized network interface that appears to be the local address.
	 * @throws IOException if an I/O error occurs.
	 */
	public static InetAddress guessNetworkAddress() throws IOException {
		InetAddress finalAddr = null;
		List<InetAddress> addrs = getAllNetworkAddresses();
		for (InetAddress addr : addrs) {
			if (addr.isLoopbackAddress() ||
				addr.isLinkLocalAddress() ||
				addr.isMulticastAddress()) {
				continue;
			}
			else if (addr.isSiteLocalAddress() && addr.isReachable(TIMEOUT)) {
				finalAddr = addr;
				return finalAddr;
			}
		}
		return finalAddr;
	}
}
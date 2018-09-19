package l1j.server.server.network;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class PacketHandler extends ChannelInboundHandlerAdapter {
	static int i = 0;

	@Override
	public void channelActive(final ChannelHandlerContext ctx) {
		Client client = new Client();
		client.channel = ctx.channel();
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) {
		// System.out.println(msg);
		System.out.println("Start read");
		readPacket((byte[]) msg);
		// m.release();

	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		cause.printStackTrace();
		ctx.close();
	}

	@Override
	public void handlerAdded(ChannelHandlerContext ctx) {
	}

	@Override
	public void handlerRemoved(ChannelHandlerContext ctx) {

	}

	public void readPacket(byte[] data) {
		System.out.println("First readable: " + data.length);
		System.out.println("og packet: " + new ByteArrayUtil(data).dumpToString());
		// System.out.println("returning");
		// System.out.println(LineageClient._clkey);
		// buf.discardReadBytes();
		if (i == 0) {
			i++;
			// System.out.println(data);
			Client.process(data);

		} else {
			try {
				// System.out.println("encrypted packet: " + new
				// ByteArrayUtil(LineageEncryption.decrypt(data, dataLength,
				// LineageClient._clkey)).dumpToString());
				Client.process(LineageEncryption.decrypt(data, data.length, Client._clkey));
			} catch (NoEncryptionKeysSelectedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// return LineageEncryption.decrypt(data, dataLength, _clkey);
		}

	}
}
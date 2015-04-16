package net.zum.slick.libs;

public class Link
{
	public String getUri(String path)
	{
		return path.replace("/content/slick", "") + ".html";
	}
}
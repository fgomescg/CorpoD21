package corpode21.com.br.corpod21.api;


import org.scribe.builder.api.DefaultApi20;
import org.scribe.extractors.AccessTokenExtractor;
import org.scribe.extractors.JsonTokenExtractor;
import org.scribe.model.OAuthConfig;
import org.scribe.model.Verb;
import org.scribe.utils.OAuthEncoder;

public class VimeoApi2 extends DefaultApi20
{

  private static final String AUTHORIZE_URL = "https://api.vimeo.com/oauth/authorize";
  private static final String SCOPED_AUTHORIZE_URL = AUTHORIZE_URL + "&scope=%s";
  
  @Override
  public String getAccessTokenEndpoint()
  {
    return "https://api.vimeo.com/oauth/access_token";
  }

    @Override
    public String getAuthorizationUrl(OAuthConfig config)
    {
        // Append scope if present
        if (config.hasScope())
        {
            return String.format(SCOPED_AUTHORIZE_URL, config.getApiKey(), OAuthEncoder.encode(config.getCallback()), OAuthEncoder.encode(config.getScope()));
        }
        else
        {
            return String.format(AUTHORIZE_URL, config.getApiKey(), OAuthEncoder.encode(config.getCallback()));
        }
    }


}

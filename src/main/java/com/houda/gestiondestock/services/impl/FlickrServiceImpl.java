package com.houda.gestiondestock.services.impl;

import com.houda.gestiondestock.services.FlickrService;
import com.flickr4java.flickr.Flickr;
import com.flickr4java.flickr.FlickrException;
import com.flickr4java.flickr.REST;
import com.flickr4java.flickr.RequestContext;
import com.flickr4java.flickr.auth.Auth;
import com.flickr4java.flickr.auth.Permission;
import com.flickr4java.flickr.uploader.UploadMetaData;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ExecutionException;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class FlickrServiceImpl implements FlickrService {

  @Value("afde4bf80f86fe6f2ff59c46d405f590")
  private String apiKey;

  @Value("6467d1a675b0c3f4")
  private String apiSecret;

  @Value("72157720874800609-14471b9303ff53b9")
  private String appKey;

  @Value("069be8a1b46261df")
  private String appSecret;

  private Flickr flickr;

  @Override
  @SneakyThrows
  public String savePhoto(InputStream photo, String title) {
    connect();
    UploadMetaData uploadMetaData = new UploadMetaData();
    uploadMetaData.setTitle(title);

    String photoId = flickr.getUploader().upload(photo, uploadMetaData);
    return flickr.getPhotosInterface().getPhoto(photoId).getMedium640Url();
  }

  private void connect() throws InterruptedException, ExecutionException, IOException, FlickrException {
    flickr = new Flickr(apiKey, apiSecret, new REST());
    Auth auth = new Auth();
    auth.setPermission(Permission.READ);
    auth.setToken(appKey);
    auth.setTokenSecret(appSecret);
    RequestContext requestContext = RequestContext.getRequestContext();
    requestContext.setAuth(auth);
    flickr.setAuth(auth);
  }

}

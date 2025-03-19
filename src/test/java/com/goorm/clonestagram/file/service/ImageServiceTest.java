package com.goorm.clonestagram.file.service;

import com.goorm.clonestagram.file.domain.Posts;
import com.goorm.clonestagram.file.dto.ImageUploadReqDto;
import com.goorm.clonestagram.file.dto.ImageUploadResDto;
import com.goorm.clonestagram.file.repository.PostsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ImageServiceTest {

    private String uploadFolder = "src/main/resources/static/uploads/";

    private ImageUploadReqDto imageUploadReqDto;

    @Mock
    private PostsRepository postsRepository;

    @InjectMocks
    private ImageService imageService;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        imageUploadReqDto = new ImageUploadReqDto();
    }

    @Test
    public void 파일생성테스트(){

        MockMultipartFile mockMultipartFile = new MockMultipartFile(
                "file", "test-image.jpg","image/jpeg","dummy image content".getBytes()
        );

        UUID uuid = UUID.randomUUID();
        String imageFileName = uuid + "_" +mockMultipartFile.getOriginalFilename();
        Path imageFilePath = Paths.get(uploadFolder + imageFileName);
        try{
            Files.createDirectories(Paths.get(uploadFolder));
            Files.write(imageFilePath, mockMultipartFile.getBytes());
            assertTrue(Files.exists(imageFilePath));
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @Test
    public void 생성완료테스트() throws Exception{
        String uploadFolder = "src/main/resources/static/uploads/";

        MockMultipartFile mockMultipartFile = new MockMultipartFile(
                "file", "test-image.jpg","image/jpeg","dummy image content".getBytes()
        );

        UUID uuid = UUID.randomUUID();
        String imageFileName = uuid + "_" +mockMultipartFile.getOriginalFilename();
        Path imageFilePath = Paths.get(uploadFolder + imageFileName);
        try{
            Files.createDirectories(Paths.get(uploadFolder));
            Files.write(imageFilePath, mockMultipartFile.getBytes());
        }catch (IOException e){
            e.printStackTrace();
        }
        Posts postImage = imageUploadReqDto.toEntity(imageFileName);

        when(postsRepository.save(any(Posts.class))).thenReturn(postImage);
        ImageUploadResDto imageUploadResDto = imageService.imageUpload(imageUploadReqDto);

        assertNotNull(imageUploadResDto);
        assertNotNull(imageUploadResDto.getCreatedAt());
        verify(postsRepository).save(any(Posts.class));
    }


}
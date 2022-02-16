# :camera: ClovaSample

> **Naver Clova OCR** API를 활용하여 촬영한 이미지 상의 글자를 인식하는 개인 프로젝트

## 1. 제작 기간

- 2021.08 (1주)

  

## 2. 사용 기술

- `Retrofit2`, ``ViewModel``, ``Naver Clova OCR``, ``CameraX``



## 3. 핵심 기능

- 사진 촬영 및 미리보기
- 촬영한 사진 API로 요청 및 인식한 text 화면 표시
  - Ex) Clova / 0.999
    -  **/** 를 기준으로 왼쪽은 인식한 텍스트 _infertext_, 오른쪽은 정확도 _inferConfidence_를 뜻합니다

<img src="https://user-images.githubusercontent.com/69448123/153759590-6b0c72f5-8495-4148-b995-85abf6fecc80.png" alt="image" style="zoom: 60%;" />

## 4. 트러블 슈팅

#### 4.1. 촬영된 사진의 텍스트 분석 응답 오류

> - 정상적으로 사진이 촬영되고 통신을 요청함에도 응답 오류가 뜨는 현상
> - 잘못된 형태로 Base64를 인코딩해 request (줄바꿈을 포함하여 요청하였음)
>   - _postman_을 사용하여 실제 request 되는 이미지의 Base64 형태를 확인. 
>   - API 요청시 줄바꿈을 포함하지 않도록 **Encoder flag를 `Base64.NO_WRAP` **으로 설정하여 해결.
>
> ```kotlin
> class MyEncoder {
>    ...
>     fun encodeImage(bm: Bitmap): String? {
> ...
>         val base64 = Base64.encodeToString(bImage, Base64.NO_WRAP)
>         return base64
>     }
> }
> ```



## 5. 회고/ 느낀 점

- API 가이드를 읽고 이해하는 것부터 쉽지 않았고, *Encoder flag* 하나 때문에 주말 내내 고민했던 프로젝트입니다.  그러나, 하나의 문제를 가지고 다각도에서 고민할 수 있었던 좋은 경험이었습니다. 
-  `CameraX`  를 활용해볼 수 있었습니다.


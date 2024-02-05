<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
 <head>
    <meta charset="UTF-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Document</title>
    <link rel="stylesheet" href="public/css/practice.css" />
  </head>
  <body>
    <div>
      <ul class="comment">
        <li class="comment-form">
          <form id="commentFrm">
            <h4>댓글쓰기 <span></span></h4>
            <span class="ps_box">
              <input type="text" placeholder="댓글 내용을 입력해주세요." class="int" name="content" />
            </span>
            <input type="submit" class="btn" value="등록" />
          </form>
        </li>
        <li id="comment-list"></li>
      </ul>
    </div>
    <script src="public/js/practice.js"></script>
  </body>
</html>
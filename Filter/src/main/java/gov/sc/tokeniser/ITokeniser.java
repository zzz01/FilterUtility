package gov.sc.tokeniser;

import java.io.IOException;
import java.util.List;

/// <summary>
   /// �ִ����ӿ�
   /// </summary>
   public interface ITokeniser
   {
       List<String> partition(String input) throws IOException;
   }

package caffe;

import caffe.config.kafka.KafkaProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class MypageViewHandler {


    @Autowired
    private MypageRepository mypageRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whenOrdered_then_CREATE_1 (@Payload Ordered ordered) {
        try {
            if (ordered.isMe()) {
                // view 객체 생성
                Mypage mypage = new Mypage();
                // view 객체에 이벤트의 Value 를 set 함
                mypage.setOrderid(ordered.getId());
                mypage.setMenuid(ordered.getMenuid());
                mypage.setQty(ordered.getQty());
                mypage.setPrice(ordered.getPrice());
                mypage.setStatus(ordered.getStatus());
                // view 레파지 토리에 save
                mypageRepository.save(mypage);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    @StreamListener(KafkaProcessor.INPUT)
    public void whenCoffeeServed_then_UPDATE_1(@Payload CoffeeServed coffeeServed) {
        try {
            if (coffeeServed.isMe()) {
                // view 객체 조회
                List<Mypage> mypageList = mypageRepository.findByOrderid(coffeeServed.getOrderid());
                for(Mypage mypage : mypageList){
                    // view 객체에 이벤트의 eventDirectValue 를 set 함
                    mypage.setMakeid(coffeeServed.getId());
                    mypage.setStatus(coffeeServed.getStatus());
                    // view 레파지 토리에 save
                    mypageRepository.save(mypage);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @StreamListener(KafkaProcessor.INPUT)
    public void whenMakeCanceled_then_UPDATE_2(@Payload MakeCanceled makeCanceled) {
        try {
            if (makeCanceled.isMe()) {
                // view 객체 조회
                List<Mypage> mypageList = mypageRepository.findByOrderid(makeCanceled.getOrderid());
                for(Mypage mypage : mypageList){
                    // view 객체에 이벤트의 eventDirectValue 를 set 함
                    mypage.setMakeid(makeCanceled.getId());
                    mypage.setStatus(makeCanceled.getStatus());
                    // view 레파지 토리에 save
                    mypageRepository.save(mypage);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
import React from "react";
import Slider from "react-slick";

const TestimonialData = [
  {
    id: 1,
    name: "Đoàn Văn Xuân Bắc",
    text: "Theo quan điểm của tôi thì đồ uống ở đây rất ngon và hợp khẩu vị của tôi. Tôi rất thích đồ uống ở đây",
    img: "https://picsum.photos/101/101",
  },
  {
    id: 2,
    name: "Cristiano Ronaldo",
    text: "Mặc dù tôi đá bóng ngu, nhưng tôi rất thích đồ uống ở đây. Đồ uống ở đây rất ngon và hợp khẩu vị của tôi",
    img: "https://picsum.photos/102/102",
  },
  {
    id: 3,
    name: "Messi",
    text: "Tôi là GOAT, nên tôi không cần khen đồ uống ở đây. Nhưng tôi vẫn thích đồ uống ở đây",
    img: "https://picsum.photos/104/104",
  },
  {
    id: 5,
    name: "Vũ Đan Trường",
    text: "Ngoài hát như shit và ăn mặc như cuk thì tôi cũng rất mê đồ uống ở đây, đặc biệt là cafe phân trồn",
    img: "https://picsum.photos/103/103",
  },
];

const Testimonials = () => {
  var settings = {
    dots: true,
    arrows: false,
    infinite: true,
    speed: 500,
    slidesToScroll: 1,
    autoplay: true,
    autoplaySpeed: 2000,
    cssEase: "linear",
    pauseOnHover: true,
    pauseOnFocus: true,
    responsive: [
      {
        breakpoint: 10000,
        settings: {
          slidesToShow: 3,
          slidesToScroll: 1,
          infinite: true,
        },
      },
      {
        breakpoint: 1024,
        settings: {
          slidesToShow: 2,
          slidesToScroll: 1,
          initialSlide: 2,
        },
      },
      {
        breakpoint: 640,
        settings: {
          slidesToShow: 1,
          slidesToScroll: 1,
        },
      },
    ],
  };

  return (
    <div className="py-10 mb-10">
      <div className="container">
        {/* header section */}
        <div className="mb-10">
          <h1
            data-aos="fade-up"
            className="text-center text-4xl font-bold font-cursive"
          >
            Testimonials
          </h1>
        </div>

        {/* Testimonial cards */}
        <div data-aos="zoom-in">
          <Slider {...settings}>
            {TestimonialData.map((data) => (
              <div className="my-6">
                <div
                  key={data.id}
                  className="flex flex-col gap-4 shadow-lg py-8 px-6 mx-4 rounded-xl  bg-primary/10 relative"
                >
                  <div className="mb-4">
                    <img
                      src={data.img}
                      alt=""
                      className="rounded-full w-20 h-20"
                    />
                  </div>
                  {/* content section */}
                  <div className="flex flex-col items-center gap-4">
                    <div className="space-y-3">
                      <p className="text-xs text-gray-500">{data.text}</p>
                      <h1 className="text-xl font-bold text-black/80  font-cursive2">
                        {data.name}
                      </h1>
                    </div>
                  </div>
                  <p className="text-black/20 text-9xl font-serif absolute top-0 right-0">
                    ,,
                  </p>
                </div>
              </div>
            ))}
          </Slider>
        </div>
      </div>
    </div>
  );
};

export default Testimonials;

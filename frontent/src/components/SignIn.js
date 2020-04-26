import React, { Component } from 'react'
import { GoogleLogin } from 'react-google-login';
import { Row, Col } from 'antd';
import TopImage from './assets/undraw_programming_2svr.svg';

export default class SignIn extends Component {

  constructor(props){
    super(props);
  }


  onSuccess = response => {
    console.log(response);
    const z = response.accessToken;
    const requestOptions = {
      method: 'POST',
      headers: { 'Content-Type': 'application/json',
    'Access-Control-Allow-Origin' : '*',
      'Access-Control-Allow-Credentials' : 'true',
     'access_token' :  response.accessToken,
     'token' : response.tokenId
    },
      body: JSON.stringify({
        profile: response.profileObj,
      })
  };
  localStorage.setItem("profile-photo", response.profileObj.imageUrl);
  fetch('http://192.168.0.103:3005/login', requestOptions)
    .then(() => {
      localStorage.setItem("token", z);
      this.props.history.push("/home");
    })
    .catch(() => {
      this.props.history.push("/login");
      // this.props.history.push("/home");
    });
  };
  onFailure = response => console.error("error", response);

  render() {
    return (
    
     // <div style={{marginLeft : '30%', marginRight : '30%', marginTop : '20%', textAlign : 'center'}}>
        /*{ <Card style={{marginLeft : '30%', marginRight : '30%', marginTop : '20%', textAlign : 'center'}}> }*/
        /*<GoogleLogin
          clientId="799554132875-eeio89ohmbec9i7krc7psu5vc509t3v1.apps.googleusercontent.com"
          buttonText="Login"
          onSuccess={this.onSuccess}
          onFailure={this.onFailure}
          cookiePolicy={'single_host_origin'}
        />*/
        /*{ </Card> }*/
     // </div>
     <div>
      {/* //  <div style = {{marginLeft : '30%', marginRight : '30%', marginTop : '20%', textAlign : 'center', height : '200%'}}>
      //    <div class = 'introText' style={{fontFamily: 'Oswald, sans-serif;', fontWeight:'bold', fontSize:'30px'}}>
      //       Welcome to the competition !
      //    </div> */}
         <Row>
          <Col span={12}>
            <img src={TopImage} style={{maxWidth : '90%', maxheight : '120%',marginLeft : '5%', marginTop: '5%'}} />
          </Col>
          <Col span={12} style={{fontFamily:'Oswald, sans-serif;', fontWeight:'bold',fontSize:'30px', marginTop:'10%'}}>
            Are you updated with the latest tech ?
          </Col>
        </Row>
        <Row>
          <Col style={{fontFamily:'Oswald, sans-serif;', fontWeight:'bold',fontSize:'20px', marginTop:'5%',marginBottom:'5%',
                    display:'flex', justifyContent:'center', alignItems:'center'}}>
            So many things to learn....
          </Col>
        </Row>
        <Row>
          <Col span={6}>

          </Col>
          <Col span={6}>

          </Col>
          <Col span={6}>

          </Col>
          <Col span={6}>
          </Col>
        </Row>
       </div>

    )
  }
}

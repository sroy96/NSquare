import React, { Component } from 'react'
import { Statistic, Row, Col, Card, Descriptions, Progress } from 'antd';
import { FieldTimeOutlined, ArrowUpOutlined, ArrowDownOutlined } from '@ant-design/icons';
import {
  AreaChart, Area, XAxis, YAxis, CartesianGrid, Tooltip,
} from 'recharts';


export default class Profile extends Component {

  render() {

    const data = [
      {
        name: '10-Dec', ds: 4000, algorithm: 2400, confidence: 2400,
      },
      {
        name: '11-Dec', ds: 3000, algorithm: 1398, confidence: 2210,
      },
      {
        name: '12-Dec', ds: 2000, algorithm: 9800, confidence: 2290,
      },
      {
        name: '13-Dec', ds: 2780, algorithm: 3908, confidence: 2000,
      },
      {
        name: '14-Dec', ds: 1890, algorithm: 4800, confidence: 2181,
      },
      {
        name: '15-Dec', ds: 2390, algorithm: 3800, confidence: 2500,
      },
      {
        name: '16-Dec', ds: 3490, algorithm: 4300, confidence: 2100,
      },
    ];

    return (
      <div>
        <br></br>
        <Row>
          <Col span={12}>
              <Card title="User Info" style={{ width: 600 }} >
                  <Descriptions title="Personal Details" bordered >
                      <Descriptions.Item label="UserName">Zhou Maomao</Descriptions.Item>
                      <Descriptions.Item label="Email Address">example@example.com</Descriptions.Item>
                  </Descriptions>
                  <br></br>

                  <Descriptions title="Code Feedback" />
                  <AreaChart
                      width={500}
                      height={300}
                      data={data}
                      margin={{
                      top: 5, right: 5, left: 0, bottom: 0,
                  }}
                      >
                  <CartesianGrid strokeDasharray="3 3" />
                  <XAxis dataKey="name" />
                  <YAxis />
                  <Tooltip />
                  <Area type="monotone" dataKey="ds" stackId="1" stroke="#a0d911" fill="#a0d911" />
                  <Area type="monotone" dataKey="algorithm" stackId="1" stroke="#1890ff" fill="#1890ff" />
                  <Area type="monotone" dataKey="confidence" stackId="1" stroke="#ffec3d" fill="#ffec3d" />
                  </AreaChart>
              </Card>


          </Col>
          <Col>
            <Card title="Geek Stat" style={{ width: 600 }}>
                <div>
                  <Row>
                    <Col span={12}>
                    <div style={{textAlign : 'center'}}>
                    <Statistic title="Time Spent " value={1128} prefix={<FieldTimeOutlined />} />
                    </div>
                    </Col>
                    <Col span={12}>
                    <div style={{textAlign : 'center'}}>
                    <Statistic title="Questions Worked" value={93} />
                    </div>
                    </Col>
                  </Row>
                </div>
                <br></br>
                <br></br>
                <div>
                  <Row>
                      <Col span={12}>
                      <div style={{textAlign : 'center'}}>
                        <Card>
                          <Statistic
                            title="Errors"
                            value={9.3}
                            precision={2}
                            valueStyle={{ color: '#cf1322' }}
                            prefix={<ArrowDownOutlined />}
                            suffix="%"
                          />
                        </Card>
                      </div>
                      </Col>
                      <Col span={12}>
                      <div style={{textAlign : 'center'}}>
                        <Card>
                          <Statistic
                            title="Accuracy"
                            value={11.28}
                            precision={2}
                            valueStyle={{ color: '#3f8600' }}
                            prefix={<ArrowUpOutlined />}
                            suffix="%"
                          />
                        </Card>
                      </div>
                      </Col>
                      <div>
                        <br></br>
                        <Descriptions title="Completions" />
                        {/* <Descriptions.Item label="UserName">sdf</Descriptions.Item> */}
                        <Progress percent={30} />
                        <p>Easy</p>
                        <Progress percent={50} />
                        <p>Medium</p>
                        <Progress percent={70} />
                        <p>Hard</p>
                      </div>
                    </Row>
                </div>
            </Card>
          </Col>
        </Row>
      </div>
    )
  }
}
